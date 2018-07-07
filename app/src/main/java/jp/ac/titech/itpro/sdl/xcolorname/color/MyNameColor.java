package jp.ac.titech.itpro.sdl.xcolorname.color;

/**
 * This is extension of {@linkplain MyColor}.
 * Color name is added as color info.
 */
public class MyNameColor extends MyColor {
    private String colorName;

    public MyNameColor(String colorName, int red, int green, int blue){
        super(red, green, blue);
        this.colorName = colorName;
    }

    public String getColorName() {
        return colorName;
    }
}
