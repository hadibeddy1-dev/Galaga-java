package game;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Gère le score actuel du joueur et la persistance du High Score dans un fichier.
 */
public class ScoreManager {
    private static final String FILE_NAME = "highscore.txt";
    private int scoreActuel = 0;
    private int highScore = 0;

    public ScoreManager() { chargerHighScore(); }

    /** Ajoute des points et met à jour le record si nécessaire. */
    public void ajouterPoints(int points) {
        this.scoreActuel += points;
        if (this.scoreActuel > this.highScore) {
            this.highScore = this.scoreActuel;
            sauvegarderHighScore();
        }
    }

    public int getScore() { 
        return scoreActuel; 
    }
    public int getHighScore() {
         return highScore; 
        }
    public void resetScore() {
         this.scoreActuel = 0;
         }
         
    /**
     * Cette fonction sert à sauvegarder le meilleur score 
     * On utilise Scanner pour la lecture et FileWriter pour l'écriture.
     */
    private void chargerHighScore() {
        try {
            File fichier = new File(FILE_NAME);
            if (fichier.exists()) {
                Scanner sc = new Scanner(fichier);
                if (sc.hasNextInt()) this.highScore = sc.nextInt();
                sc.close();
            }
        } catch (Exception e) { System.err.println("Impossible de charger le HighScore"); }
    }
        
    /**
     * On écrase le fichier actuel avec la nouvelle valeur du highscore.
     */
    private void sauvegarderHighScore() {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            writer.write("" + this.highScore);
        } catch (Exception e) { e.printStackTrace(); }
    }
}