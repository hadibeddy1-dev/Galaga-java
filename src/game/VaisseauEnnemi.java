package game;

/**
 * Classe abstraite représentant un vaisseau ennemi.
 * Gère les comportements de base comme le tir et les états de mouvement (Formation, Attaque, Retour).
 */
public abstract class VaisseauEnnemi extends Entite {
    protected int valeur;
    protected EtatEnnemi etat;
    protected double vitesseAttaque = 0.01; 

    public VaisseauEnnemi(double x, double y, double largeur, double vitesse, int valeur) {
        super(x, y, largeur, vitesse);
        this.valeur = valeur;
        this.etat = EtatEnnemi.FORMATION;
    }

    public abstract void draw();

    /**
     * Génère un nouveau tir ennemi dirigé vers le bas.
     * @return Un objet Tir.
     */
    public Tir tirer() {
        return new Tir(this.x, this.y, -1);
    }

    @Override
    public void update() {
        switch (etat) {
            case FORMATION:
                break;
            case ATTAQUE:
                this.y -= vitesseAttaque;
                if (this.y < -0.1) {
                    this.etat = EtatEnnemi.RETOUR;
                }
                break;
            case RETOUR:
                break;
        }
    }

    public void deplacer(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    public int getPoints() { return valeur; }
    public EtatEnnemi getEtat() { return etat; }
    public void setEtat(EtatEnnemi etat) { this.etat = etat; }
}