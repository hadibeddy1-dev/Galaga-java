package game;

/**
 * Classe abstraite représentant une entité générique dans le jeu Galaga.
 * Sert de base pour tous les objets mobiles (joueur, ennemis, tirs).
 */
public abstract class Entite {

    protected double x;
    protected double y;
    protected double largeur;
    protected double vitesse;

    /**
     * Constructeur d'une entité.
     * @param x Position horizontale initiale.
     * @param y Position verticale initiale.
     * @param largeur Taille de l'entité.
     * @param vitesse Vitesse de déplacement.
     */
    public Entite(double x, double y, double largeur, double vitesse) {
        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.vitesse = vitesse;
    }

    public double getX(){ return this.x; }
    public double getY(){ return this.y; }
    public double getLargeur(){ return this.largeur; }

    /** Met à jour la logique de l'entité. */
    public abstract void update();
    /** Gère l'affichage de l'entité. */
    public abstract void draw();

    public void setY(double y) { this.y = y; }
    public void setX(double x) { this.x = x; }
}