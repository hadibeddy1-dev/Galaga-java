package game;

import engine.StdDraw;
import game.actors.Player;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * La classe principale du jeu.
 * Orchestre la boucle de jeu, gère les états globaux, l'affichage et la logique.
 */
public class Game {
    private static final int NIVEAU_MAX = 3;
    private static final int NIVEAU_DEPART = 1;

    public Player player;

    private GestionnaireTirs gestionTirsJoueur;
    private GestionnaireTirs gestionTirsEnnemis;
    private Sky sky;
    private Formation formation;

    private ScoreManager scoreManager;
    private int score = 0;
    private int highScore = 0;
    private boolean gameOver = false;
    private int numeroNiveauActuel = NIVEAU_DEPART;
    private int timerGameOver = 0;

    private boolean enIntroduction = true;
    private int timerIntro = 0;
    private String nomJoueur = "PILOTE";

    private boolean enTransition = false;
    private int timerTransition = 0;
    private String messageTransition = "";

    private static Sprite spriteL = new Sprite("level.spr");
    private static Sprite spriteV = new Sprite("ship.spr");
    private ArrayList<ScoreEffect> effetsVisuels;

    /**
     * Constructeur de la classe Game.
     */
    public Game() {
        this.sky = new Sky(100);
        this.player = new Player(0.5, 0.17, 0.05);
        this.gestionTirsJoueur = new GestionnaireTirs();
        this.gestionTirsEnnemis = new GestionnaireTirs();
        this.effetsVisuels = new ArrayList<>();
        this.scoreManager = new ScoreManager();
        this.formation = new Formation(numeroNiveauActuel);
        chargerNiveau(numeroNiveauActuel);
    }
    /**
     * Initialise la fenêtre de rendu.
     */
    private void init() {
        StdDraw.setCanvasSize(700, 700);
        StdDraw.enableDoubleBuffering();
    }
    /**
     * Lance la boucle principale du jeu.
     */
    public void launch() {
        init();
        while (true) {
            StdDraw.clear(StdDraw.BLACK);
            if (enIntroduction) {
                sky.dessiner();
                if (formation != null) {
                    for (VaisseauEnnemi e : formation.getEnnemis()) {
                        e.draw();
                    }
                }
                player.draw();
                drawHUD();
                StdDraw.setPenColor(StdDraw.YELLOW);
                StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 40));
                StdDraw.text(0.5, 0.5, "PRÊT " + nomJoueur + " ?");
                StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 20));
                StdDraw.text(0.5, 0.45, "Le combat commence !");
                timerIntro++;
                if (timerIntro > 100) {
                    enIntroduction = false;
                }
            }
            else if (!gameOver) {
                update();
                draw();
            }
            else {
                drawGameOver();
                timerGameOver++;
                if (timerGameOver > 30) {
                    if ((timerGameOver / 10) % 2 == 0) {
                        StdDraw.text(0.5, 0.25, "Appuyez sur ESPACE pour rejouer");
                    }
                    if (StdDraw.isKeyPressed(32)) {
                        enTransition = false;
                        recommencerPartie();
                    }
                }
            }
            StdDraw.show();
            StdDraw.pause(20);
        }
    }

    private void demarrerTransition() {
        enTransition = true;
        timerTransition = 0;
        gestionTirsEnnemis.vider();
        gestionTirsJoueur.vider();
        if (numeroNiveauActuel < NIVEAU_MAX) {
            messageTransition = "Niveau " + numeroNiveauActuel + " terminé ! Accrochez-vous...";
        } else {
            messageTransition = "MISSION ACCOMPLIE ! Analyse des résultats...";
        }
    }
    private void update() {
        if (enTransition) {
            timerTransition++;       
            if (timerTransition > 100) { 
                enTransition = false;
                if (numeroNiveauActuel == NIVEAU_MAX) {
                    this.gameOver = true; 
                    drawGameOver();
                } else {
                    numeroNiveauActuel++;
                    chargerNiveau(numeroNiveauActuel);
                }
            }
            return; 
        }
    if (formation.getEnnemis().isEmpty() && !enTransition) {
        demarrerTransition();
        return;
    }
        player.update();
        sky.update();
        formation.update();
        gestionTirsJoueur.update();
        gestionTirsEnnemis.update();
        effetsVisuels.removeIf(effet -> effet.estFini());
        for (ScoreEffect effet : effetsVisuels) {
            effet.update();
        }
    gererCreationTirs();
    int pointsGagnes = Collision.traiterTirsJoueur(gestionTirsJoueur, formation, effetsVisuels);
        if (pointsGagnes > 0) {
            score += pointsGagnes;
            if (score > highScore) {
                highScore = score;
                scoreManager.ajouterPoints(pointsGagnes);
            }
        }
        if (Collision.traiterTirsEnnemis(gestionTirsEnnemis, player)) {
            player.perdreVie();
            verifierMortJoueur();
        }
        if (Collision.traiterKamikaze(formation, player)) {
            player.die();
            verifierMortJoueur();
        }
}
    private void verifierMortJoueur() {
        if (player.getVies() <= 0) {
            gameOver = true;
        }
    }

    private void gererCreationTirs() {
        if (StdDraw.isKeyPressed(32) && gestionTirsJoueur.taille() < 3) {
            gestionTirsJoueur.ajouterTir(new Tir(player.getX(), player.getY(), 1));
            StdDraw.pause(80);
        }
        if (Math.random() < 0.02) {
            List<VaisseauEnnemi> tireurs = formation.getTireursPotentiels();
            if (!tireurs.isEmpty()) {
                int index = (int) (Math.random() * tireurs.size());
                gestionTirsEnnemis.ajouterTir(tireurs.get(index).tirer());
            }
        }
    }

    public void draw() {
        sky.dessiner();
        player.draw();
        if (enTransition) {
            drawHUD();
            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 30));
            StdDraw.text(0.5, 0.6, "BRAVO !");
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 22));
            StdDraw.text(0.5, 0.5, messageTransition);
            return;
        }
        if (formation != null) {
            for (VaisseauEnnemi e : formation.getEnnemis()) {
                e.draw();
            }
        }
        for (ScoreEffect effet : effetsVisuels) {
            effet.draw();
        }
        gestionTirsJoueur.draw();
        gestionTirsEnnemis.draw();
        drawHUD();
    }

    public void drawHUD() {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setPenRadius(0.003);
        StdDraw.line(0.0, 0.90, 1.0, 0.90);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
        StdDraw.textLeft(0.05, 0.95, "SCORE: " + score);
        StdDraw.text(0.50, 0.95, "HIGH SCORE: " + highScore);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.line(0.00, 0.13, 1.0, 0.13);
        double axe_x = 0.05;
        double axe_y = 0.05;
        double size = 0.04;
        double space = 0.05;
        int life = player.getVies();
        for (int i = 0; i < life; i++) {
            spriteV.draw(axe_x + (i * space), axe_y, size);
        }
        double startX = 0.95;
        for (int i = 0; i < numeroNiveauActuel; i++) {
            spriteL.draw(startX - (i * space), axe_y, size);
        }
    }

    private void drawGameOver() {
        if (numeroNiveauActuel == NIVEAU_MAX && player.getVies() > 0) {
            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 50));
            StdDraw.text(0.5, 0.6, "YOU WIN !");
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 20));
            StdDraw.text(0.5, 0.5, "La galaxie est sauvée, pilote !");
        } else {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 50));
            StdDraw.text(0.5, 0.6, "GAME OVER");
        } 
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 20));
        StdDraw.text(0.5, 0.4, "Score final : " + score);
    }

    /** 
     * @param numero
     */
    public void chargerNiveau(int numero) {
    gestionTirsEnnemis.vider();
    gestionTirsJoueur.vider();
    if (effetsVisuels != null) effetsVisuels.clear();
    if (player != null) player.setX(0.5);
    formation = new Formation(numero);
    File fichier = trouverFichierNiveau(numero);

    if (fichier.exists()) {
        try (Scanner sc = new Scanner(fichier)) {
            while (sc.hasNextLine()) {
                String ligne = sc.nextLine().trim();
                if (ligne.startsWith("#") || ligne.isEmpty() || ligne.startsWith("Level")) continue;
                
                String[] mots = ligne.split("\\s+");
                if (mots.length >= 3) {
                    formation.ajouterEnnemi(mots[0], Double.parseDouble(mots[1]), Double.parseDouble(mots[2])); 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
    else {
        System.out.println("Fichier du niveau " + numero + " introuvable. Génération automatique du niveau " + numero);
        for (double x = 0.2; x <= 0.8; x += 0.15) {
            formation.ajouterEnnemi("bee", x, 0.8);        
            formation.ajouterEnnemi("butterfly", x, 0.7);  
        }
    }
}

    private File trouverFichierNiveau(int numero) {
        File fichier = new File("Galaga/ressources/levels/level" + numero + ".lvl");
        if (fichier.exists()) {
            return fichier;
        }
        return new File("ressources/levels/level" + numero + ".lvl");
    }

    public void recommencerPartie() {
        score = 0;
        gameOver = false;
        enTransition = false;
        timerTransition = 0;
        numeroNiveauActuel = NIVEAU_DEPART;
        player = new Player(0.5, 0.17, 0.05);
        chargerNiveau(numeroNiveauActuel);
    }
}
