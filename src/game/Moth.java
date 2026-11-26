package game;

/**
 * Représente un ennemi de type "Mite".
 * Possède un comportement de descente et remontée lente en formation au niveau 2.
 */
public class Moth extends VaisseauEnnemi {
    private static Sprite sprite = new Sprite("catcher.spr"); 
    private int niveau;
    private boolean enDescente = true; 

    public Moth(double x, double y, int niveau) {
        super(x, y, 0.05, 0.002, 300); 
        this.niveau = niveau;
    }

    @Override
    public void draw() { sprite.draw(x, y, largeur); }

    @Override
    public void update() {
        if (this.etat == EtatEnnemi.FORMATION && this.niveau >= 2) {
            if (enDescente) {
                this.y -= 0.002;
                if (this.y < 0.5) enDescente = false;
            } else {
                this.y += 0.002;
                if (this.y > 0.85) enDescente = true;
            }
        } else {
            super.update();
        }
    }
}