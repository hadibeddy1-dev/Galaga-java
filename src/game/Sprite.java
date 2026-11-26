package game;

import engine.StdDraw;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Gère le chargement et le rendu des graphismes pixelisés à partir de fichiers .spr.
 */
public class Sprite {
    private Color[][] pixels;
    private int width;
    private int height;
    private static final Map<Character, Color> PALETTE = new HashMap<>();

    static {
        PALETTE.put('R', StdDraw.RED);
        PALETTE.put('G', StdDraw.GREEN);
        PALETTE.put('B', StdDraw.BLUE);
        PALETTE.put('Y', StdDraw.YELLOW);
        PALETTE.put('W', StdDraw.WHITE);
        PALETTE.put('O', StdDraw.ORANGE); 
        PALETTE.put('P', StdDraw.PINK);
    }

    public Sprite(String fileName) { chargerSprite(fileName); }

    /** Charge les données du sprite depuis le dossier ressources. */
    private void chargerSprite(String fileName) {
        String chemin = "Galaga/ressources/sprites/" + fileName;
        File fichier = new File(chemin);
        if (!fichier.exists()) return;

        try (Scanner sc = new Scanner(fichier)) {
            ArrayList<String> lignes = new ArrayList<>();
            while (sc.hasNextLine()) {
                String ligne = sc.nextLine(); 
                if (!ligne.isEmpty()) lignes.add(ligne);
            }
            if (lignes.isEmpty()) return;
            this.height = lignes.size();
            this.width = lignes.get(0).length();
            this.pixels = new Color[height][width];
            for (int y = 0; y < height; y++) {
                String ligne = lignes.get(y);
                for (int x = 0; x < width; x++) {
                    if (x < ligne.length()) this.pixels[y][x] = PALETTE.get(ligne.charAt(x));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    /** Dessine le sprite sur le canevas. */
    public void draw(double centerX, double centerY, double totalWidth) {
        if (pixels == null) return;
        double pixelSize = totalWidth / width;
        double startX = centerX - (totalWidth / 2);
        double startY = centerY + (height * pixelSize / 2);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Color c = pixels[row][col];
                if (c != null) {
                    StdDraw.setPenColor(c);
                    StdDraw.filledSquare(startX + (col * pixelSize) + (pixelSize/2), 
                                         startY - (row * pixelSize) - (pixelSize/2), 
                                         pixelSize / 2 + 0.0005);
                }
            }
        }
    }
}