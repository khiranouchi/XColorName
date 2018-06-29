package jp.ac.titech.itpro.sdl.xcolorname.color;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import com.google.common.collect.TreeMultimap;

public class ColorSimilarity {

    // Do not craete instance of this class. Use a class which extends this class.
    protected ColorSimilarity() {
    }

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

    public double getDiff(MyColor color1, MyColor color2) {
        return 0;
    }
}
