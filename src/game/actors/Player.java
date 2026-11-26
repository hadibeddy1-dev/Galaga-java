package game.actors;

import engine.StdDraw;
import game.Entite;
import game.Sprite;

/**
 * Représente le vaisseau contrôlé par le joueur.
 * Cette classe gère les déplacements horizontaux, l'affichage via un sprite 
 * et le système de points de vie du joueur.
 */
public class Player extends Entite {
    /** Nombre de points de vie restants du joueur. */
    private int vies;
    
    /** Sprite statique utilisé pour représenter le vaisseau du joueur à l'écran. */
    private static Sprite sprite = new Sprite("ship.spr");

    /**
     * Constructeur de la classe Player.
     * Initialise la position, la taille et la vitesse du joueur, et définit ses vies à 3 par défaut.
     * * @param x       La coordonnée initiale sur l'axe horizontal (0.0 à 1.0).
     * @param y       La coordonnée initiale sur l'axe vertical (0.0 à 1.0).
     * @param vitesse La vitesse de déplacement du joueur.
     */
    public Player(double x, double y, double vitesse) {
        super(x, y, 0.06, vitesse); 
        this.vies = 3;
    }

    /**
     * Affiche le joueur à sa position actuelle en utilisant son sprite.
     */
    @Override
    public void draw() {
        sprite.draw(x, y, largeur);
    }

    /**
     * Met à jour la position du joueur en fonction des entrées clavier.
     * Vérifie les touches fléchées (37 pour gauche, 39 pour droite) et applique 
     * des limites de déplacement pour maintenir le joueur sur l'écran.
     */
    @Override
    public void update() {
        double speed = 0.01; 
        if (StdDraw.isKeyPressed(37) && x > 0.03) { // Limite gauche
            x -= speed;
        }
        if (StdDraw.isKeyPressed(39) && x < 0.97) { // Limite droite
            x += speed;
        }
    }

    /**
     * Retourne le nombre de points de vie actuels du joueur.
     * * @return Le nombre de vies restantes.
     */
    public int getVies() { 
        return this.vies; 
    }

    /**
     * Réduit le nombre de points de vie du joueur de un.
     */
    public void perdreVie() { 
        this.vies--;
    }

    /**
     * Tue instantanément le joueur en mettant ses points de vie à zéro.
     */
    public void die(){
        this.vies = 0;
    }

    /**
     * Modifie la position horizontale du joueur.
     * * @param x La nouvelle coordonnée X.
     */
    public void setX(double x){
         this.x = x; 
    }

    /**
     * Ajoute un point de vie au joueur.
     */
    public void gagnerVie() {
        this.vies++;
    }
}