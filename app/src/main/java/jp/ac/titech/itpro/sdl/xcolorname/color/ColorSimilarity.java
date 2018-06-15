package jp.ac.titech.itpro.sdl.xcolorname.color;

import java.util.List;

public abstract class ColorSimilarity {
    public abstract List<MyNameColor> getSimilarColor(MyColor originalColor);
    public abstract double getDiff(MyColor color1, MyColor color2);
}
