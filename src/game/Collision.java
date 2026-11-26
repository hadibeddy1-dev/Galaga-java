package game;

import game.actors.Player;
import java.util.Iterator;
import java.util.List;

/**
 * Classe utilitaire pour la détection et la gestion des collisions entre entités.
 */
public class Collision {

    /**
     * Vérifie si deux entités se touchent en calculant la distance entre leurs centres.
     * @return true si les entités entrent en collision.
     */
    public static boolean seTouchent(Entite a, Entite b) {
        if (a == null || b == null) return false;
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        double rayonA = a.getLargeur() / 2;
        double rayonB = b.getLargeur() / 2;
        return distance < (rayonA + rayonB);
    }

    /** Gère les impacts des tirs du joueur sur les ennemis. */
    public static int traiterTirsJoueur(GestionnaireTirs gestionTirs, Formation formation, List<ScoreEffect> effets) {
        int scoreGagne = 0;
        Iterator<Tir> itTirs = gestionTirs.getListe().iterator();
        while (itTirs.hasNext()) {
            Tir t = itTirs.next();
            boolean tirTouche = false;
            Iterator<VaisseauEnnemi> itEnnemis = formation.getEnnemis().iterator();
            while (itEnnemis.hasNext()) {
                VaisseauEnnemi e = itEnnemis.next();
                if (seTouchent(t, e)) {
                    int pointsActuels = e.getPoints();
                    scoreGagne += pointsActuels;
                    if (effets != null) effets.add(new ScoreEffect(e.getX(), e.getY(), pointsActuels));
                    itEnnemis.remove(); 
                    tirTouche = true;
                    break; 
                }
            }
            if (tirTouche) itTirs.remove();
        }
        return scoreGagne;
    }

    /** Gère les impacts des tirs ennemis sur le joueur. */
    public static boolean traiterTirsEnnemis(GestionnaireTirs gestionTirs, Player player) {
        Iterator<Tir> it = gestionTirs.getListe().iterator();
        while (it.hasNext()) {
            if (seTouchent(it.next(), player)) {
                it.remove(); 
                return true; 
            }
        }
        return false;
    }
    public static boolean traiterKamikaze(Formation formation, Player player) {
        for (VaisseauEnnemi e : formation.getEnnemis()) {
            if (seTouchent(e, player)) {
                e.setEtat(EtatEnnemi.RETOUR); 
                return true;
            }
        }
        return false;
    }
}