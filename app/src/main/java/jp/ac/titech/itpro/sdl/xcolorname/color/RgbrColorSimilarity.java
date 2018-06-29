package jp.ac.titech.itpro.sdl.xcolorname.color;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import com.google.common.collect.TreeMultimap;

/**
 * Calculate difference as
 * diff^2 = 2 * (r2-r1)^2 + 4 * (g2-g1)^2 + 3 * (b2-b1)^2
 */
public class RgbrColorSimilarity extends ColorSimilarity {

    @Override
    public List<MyNameColor> getSimilarColor(MyColor originalColor, NameSet nameSet) {
        List<MyNameColor> potenColors = nameSet.getColors();

        TreeMultimap<Double, MyNameColor> resultColors = TreeMultimap.create(
                new Comparator<Double>() {
                    @Override
                    public int compare(Double o1, Double o2) {
                        return o1.compareTo(o2);
                    }
                },
                new Comparator<MyNameColor>() {
                    @Override
                    public int compare(MyNameColor o1, MyNameColor o2) {
                        return 1;
                    }
                }
                // considered as different colors anytime
        );
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
