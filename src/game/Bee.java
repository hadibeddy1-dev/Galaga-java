package game;

/**
 * Représente un ennemi de type "Abeille".
 * Possède un comportement de vol en zigzag lors d'une attaque.
 */
public class Bee extends VaisseauEnnemi {
    private static Sprite sprite = new Sprite("bee.spr");

    public Bee(double x, double y) {
        super(x, y, 0.05, 0.002, 100); 
    }

    @Override
    public void draw() {
        sprite.draw(x, y, largeur);
    }
    
    @Override
    public void update() {
        if (this.etat == EtatEnnemi.ATTAQUE) {            
            this.y -= 0.0015; 
            // effet zigzag 
            this.x += Math.sin(this.y * 30) * 0.01;
            if (this.y < -0.1) this.etat = EtatEnnemi.MORT;
        } else {
            super.update();
        }
    }
}