package game;

import engine.StdDraw;

public class Tir extends Entite {

    private int direction; 

    public Tir(double x, double y, int direction) {
        super(x, y, 0.01, 0.02); 
        this.direction = direction;
        if (direction == 1) {
            this.vitesse = 0.07; 
        } else {
            this.vitesse = 0.03;         } 
    }

    @Override
    public void update() {
        y += vitesse * direction;
    }

    @Override
    public void draw() {
        if (direction == 1) {
            StdDraw.setPenColor(StdDraw.WHITE); 
        } else {
            StdDraw.setPenColor(StdDraw.RED);  
        }
        StdDraw.filledRectangle(x, y, 0.002, 0.015);
    }

    public boolean horsEcran() {
        return y > 1.1 || y < -0.1;
    }
}