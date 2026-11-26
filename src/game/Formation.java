package game;

import java.util.ArrayList;
import java.util.List;

/**
 * Gère le groupe d'ennemis, leur apparition et leurs mouvements collectifs.
 */
public class Formation {
    private ArrayList<VaisseauEnnemi> ennemis;
    private double directionX = 1; 
    private double vitesseFormation = 0.002; 
    private int niveauActuel;

    public Formation(int niveau) {
        this.ennemis = new ArrayList<>();
        this.niveauActuel = niveau;
    }

    /** Ajoute un ennemi spécifique à la formation. */
    public void ajouterEnnemi(String type, double x, double y) {
        switch (type.toLowerCase()) {
            case "bee": ennemis.add(new Bee(x, y)); break;
            case "butterfly": ennemis.add(new Butterfly(x, y)); break;
            case "moth": ennemis.add(new Moth(x, y, this.niveauActuel)); break;
        }
    }

    /** Met à jour le mouvement de groupe et gère les rebonds sur les bords de l'écran. */
    public void update() {
        double minX = 1.0, maxX = 0.0;
        boolean formationActive = false;
        for (VaisseauEnnemi e : ennemis) {
            if (e.getEtat() == EtatEnnemi.FORMATION) {
                if (e.getX() < minX) minX = e.getX();
                if (e.getX() > maxX) maxX = e.getX();
                formationActive = true;
            }
        }
        if (formationActive && ((maxX > 0.95 && directionX > 0) || (minX < 0.05 && directionX < 0))) {
            directionX *= -1;
        }
        double deplacement = vitesseFormation * directionX;
        for (VaisseauEnnemi e : ennemis) {
            if (e.getEtat() == EtatEnnemi.FORMATION) e.deplacer(deplacement, 0);
            e.update();
        }
        ennemis.removeIf(e -> e.getY() < -0.1 || e.getEtat() == EtatEnnemi.MORT);
        if (niveauActuel >= 2 && Math.random() < 0.05) lancerAttaque();
    }

    private void lancerAttaque() {
        List<VaisseauEnnemi> candidats = getTireursPotentiels();
        if (!candidats.isEmpty()) {
            candidats.get((int)(Math.random() * candidats.size())).setEtat(EtatEnnemi.ATTAQUE);
        }
    }

    /** 
     * @return List<VaisseauEnnemi>
     */
    public List<VaisseauEnnemi> getTireursPotentiels() {
        ArrayList<VaisseauEnnemi> liste = new ArrayList<>();
        for (VaisseauEnnemi e : ennemis) {
            if (e.getEtat() == EtatEnnemi.FORMATION) liste.add(e);
        }
        return liste;
    }

    public ArrayList<VaisseauEnnemi> getEnnemis() { return ennemis; }
}