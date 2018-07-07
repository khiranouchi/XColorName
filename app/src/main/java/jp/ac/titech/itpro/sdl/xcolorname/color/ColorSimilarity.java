package jp.ac.titech.itpro.sdl.xcolorname.color;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import com.google.common.collect.TreeMultimap;

/**
 * This class is to get the similar colors of specified color.
 * Similar color is chosen from specified color nameset.
 * Do not craete instance of this class. Use a class which extends this class. <br>
 * To create new color similarity type,
 * extend this class and override {@linkplain ColorSimilarity#getDiff} to define method of calculating color diff.
 * @see MyColor
 * @see MyFilterableColor
 * @see MyNameColor
 * @see NameSet
 */
public class ColorSimilarity {

    protected ColorSimilarity() {
    }

    /**
     * Get similar colors of specified color.
     * @param originalColor you can get similar colors of this color
     * @param nameSet color nameset (similar colors will be chosen from this nameset)
     * @return similar colors
     */
    public List<MyNameColor> getSimilarColor(MyColor originalColor, NameSet nameSet){
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

    /**
     * Get color diff of two colors.
     * @param color1
     * @param color2
     * @return color diff
     */
    public double getDiff(MyColor color1, MyColor color2) {
        return 0;
    }
}
