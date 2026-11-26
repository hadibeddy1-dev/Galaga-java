package game;

/**
 * Représente un ennemi de type "Papillon".
 * Se déplace verticalement lors d'une attaque.
 */
public class Butterfly extends VaisseauEnnemi {
    private static Sprite sprite = new Sprite("butterfly.spr");

    public Butterfly(double x, double y) {
        super(x, y, 0.05, 0.002, 200); 
    }

    @Override
    public void draw() {
        sprite.draw(x, y, largeur);
    }

    @Override
    public void update() {
        if (this.etat == EtatEnnemi.ATTAQUE) {
            this.y -= 0.015; // descente verticale rapide
            if (this.y < -0.1) this.etat = EtatEnnemi.MORT;
        } else {
            super.update();
        }
    }
}