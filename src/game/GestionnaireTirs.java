package game;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Gère une collection de projectiles (tirs alliés ou ennemis).
 * Assure leur mise à jour et leur suppression lorsqu'ils sortent de l'écran.
 */
public class GestionnaireTirs {
    private ArrayList<Tir> tirs;

    public GestionnaireTirs() { this.tirs = new ArrayList<>(); }

    public void ajouterTir(Tir tir) { tirs.add(tir); }

    /** Met à jour la position de tous les tirs et nettoie ceux hors-écran. */
    public void update() {
        Iterator<Tir> it = tirs.iterator();
        while (it.hasNext()) {
            Tir t = it.next();
            t.update();
            if (t.horsEcran()) it.remove();
        }
    }

    public void draw() {
        for (Tir t : tirs) t.draw();
    }

    public void vider() { tirs.clear(); }
    public int taille() { return tirs.size(); }
    public ArrayList<Tir> getListe() { return tirs; }
}