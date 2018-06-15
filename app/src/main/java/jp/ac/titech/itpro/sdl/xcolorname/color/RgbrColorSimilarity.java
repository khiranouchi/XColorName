package jp.ac.titech.itpro.sdl.xcolorname.color;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Calculate difference as
 * diff^2 = 2 * (r2-r1)^2 + 4 * (g2-g1)^2 + 3 * (b2-b1)^2
 */
public class RgbrColorSimilarity extends ColorSimilarity {

    @Override
    public List<MyNameColor> getSimilarColor(MyColor originalColor) {
        NameSet nameSet = new JisNameSet();
        List<MyNameColor> potenColors = nameSet.getColors();

        TreeMap<Double, MyNameColor> resultColors = new TreeMap<>();
        for(MyNameColor potenColor: potenColors){
            double diff = getDiff(originalColor, potenColor);
            resultColors.put(diff, potenColor); //automatically put in ascending order
        }

        return new ArrayList<>(resultColors.values()); //order remains? <<<TODO
    }

    @Override
    public double getDiff(MyColor color1, MyColor color2) {
        int dr = color2.getRed() - color1.getRed();
        int dg = color2.getGreen() - color1.getGreen();
        int db = color2.getBlue() - color1.getBlue();
        return 2*dr*dr + 4*dg*dg + 3*db*db;
    }
}
