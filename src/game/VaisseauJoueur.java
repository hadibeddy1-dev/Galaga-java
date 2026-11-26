package game;

import java.io.File;
import java.util.Scanner;

/**
 * Définit les propriétés de base d'un vaisseau contrôlé par le joueur.
 * Gère notamment le chargement des ressources graphiques et les points de vie.
 */
public abstract class VaisseauJoueur extends Entite {
 public int vie;

    public VaisseauJoueur(double x, double y) {
        super(x, y, 30, 5); 
        this.vie = 3;
        chargerImage();
    }

    /**
    Charge le fichier sprite correspondant  
    */
    public void chargerImage() {
        try {
            File monFichier = new File("ressources/sprites/ship.spr");
            Scanner lecteur = new Scanner(monFichier);

            while (lecteur.hasNextLine()) {
                String ligne = lecteur.nextLine();
                System.out.println(ligne); 
            }
            
            lecteur.close();
            
        } catch (Exception e) {
            System.out.println("Erreur lecture fichier ship.spr");
        }
    }
}