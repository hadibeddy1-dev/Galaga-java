package game;

import engine.StdDraw;
import java.awt.Font;

/**
 * Gère un effet visuel temporaire affichant les points gagnés à l'endroit d'une destruction.
 */
public class ScoreEffect {
    private double x, y;
    private String texte;
    private int dureeDeVie; 

    public ScoreEffect(double x, double y, int points) {
        this.x = x; this.y = y;
        this.texte = "+" + points;
        this.dureeDeVie = 40; 
    }

    public void update() {
        this.y += 0.002; 
        this.dureeDeVie--;
    }

    public void draw() {
        if (dureeDeVie > 0) {
            StdDraw.setPenColor(StdDraw.YELLOW);
            Font fontOriginale = StdDraw.getFont();
            StdDraw.setFont(new Font("Arial", Font.BOLD, 15));
            StdDraw.text(x, y, texte);
            StdDraw.setFont(fontOriginale);
        }
    }

    public boolean estFini() { return dureeDeVie <= 0; }
}