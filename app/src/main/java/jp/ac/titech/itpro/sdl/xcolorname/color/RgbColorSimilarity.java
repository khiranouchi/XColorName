package jp.ac.titech.itpro.sdl.xcolorname.color;

/**
 * One of the methods to calculate color similarity.
 * Calculate difference as "diff^2 = (r2-r1)^2 + (g2-g1)^2 + (b2-b1)^2"
 */
public class RgbColorSimilarity extends ColorSimilarity {

    @Override
    public double getDiff(MyColor color1, MyColor color2) {
        int dr = color2.getRed() - color1.getRed();
        int dg = color2.getGreen() - color1.getGreen();
        int db = color2.getBlue() - color1.getBlue();
        return dr*dr + dg*dg + db*db;
    }
}
