package jp.ac.titech.itpro.sdl.xcolorname.color;

import java.util.List;

public interface ColorSimilarity {
    public List<MyNameColor> getSimilarColor(MyColor originalColor);
    public double getDiff(MyColor color1, MyColor color2);
}
