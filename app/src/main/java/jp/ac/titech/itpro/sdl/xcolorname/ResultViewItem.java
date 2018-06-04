package jp.ac.titech.itpro.sdl.xcolorname;

public class ResultViewItem {
    private int color; //kari<<< TODO
    private String colorName;
    private String colorRgb; //string(for cache ?)<<<

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
