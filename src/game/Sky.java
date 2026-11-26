package game;

import engine.StdDraw;

/**
 * Gère l'arrière-plan étoilé du jeu.
 * Simule un défilement spatial avec des points blancs se déplaçant vers le bas.
 */
public class Sky {
    private double[] starsX;
    private double[] starsY;
    private int nombreEtoiles;

    /**
     * Initialise le ciel avec un nombre précis d'étoiles aux positions aléatoires.
     * @param nombre Nombre d'étoiles à générer.
     */
    public Sky(int nombre) {
        this.nombreEtoiles = nombre;
        this.starsX = new double[nombre];
        this.starsY = new double[nombre];

        for (int i = 0; i < nombre; i++) {
            this.starsX[i] = Math.random(); 
            this.starsY[i] = Math.random();
        }
    }

    /** Dessine toutes les étoiles sur le canevas. */
    public void dessiner() {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setPenRadius(0.001);
        for (int i = 0; i < nombreEtoiles; i++) {  
            StdDraw.point(starsX[i], starsY[i]);
        }
    }
    
    /** Met à jour la position des étoiles pour créer l'effet de défilement. */
    public void update() {
        for (int i = 0; i < nombreEtoiles; i++) {
            starsY[i] = starsY[i] - 0.0015;
            if (starsY[i] < 0) {
                starsY[i] = 1.0;
                starsX[i] = Math.random();
            }
        }
    }
}
