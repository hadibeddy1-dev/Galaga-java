package game;

import java.io.File;
import java.util.Scanner;

/**
 * Charge la configuration d'un niveau depuis un fichier texte.
 */
public class LevelLoader {

    /**
     * Lit un fichier de niveau et peuple la formation d'ennemis.
     * @param numero Le numéro du niveau à charger.
     * @param formation La formation à remplir.
     */
    public static void chargerNiveau(int numero, Formation formation) {
        String chemin = "ressources/levels/level" + numero + ".lvl"; 
        File fichier = new File(chemin);
        if (!fichier.exists()) return;

        try (Scanner sc = new Scanner(fichier)) {
            while (sc.hasNextLine()) {
                String ligne = sc.nextLine();
                if (ligne.startsWith("#") || ligne.isEmpty()) continue; 
                String[] mots = ligne.split(" ");
                if (mots.length >= 3) {
                    formation.ajouterEnnemi(mots[0], Double.parseDouble(mots[1]), Double.parseDouble(mots[2]));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}