package jp.ac.titech.itpro.sdl.xcolorname;

/**
 * Define items which is contained in one item of resultView in {@linkplain EditActivity}.
 */
public class ResultViewItem {
    private int color;
    private String colorName;
    private String colorRgb;

    public ResultViewItem(int color, String colorName, String colorRgb) {
        this.color = color;
        this.colorName = colorName;
        this.colorRgb = colorRgb;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public void setColorRgb(String colorRgb) {
        this.colorRgb = colorRgb;
    }

    public int getColor() {
        return color;
    }

    public String getColorName() {
        return colorName;
    }

    public String getColorRgb() {
        return colorRgb;
    }
}
