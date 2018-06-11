package jp.ac.titech.itpro.sdl.xcolorname.color;

public class MyColor {
    private int intColor;//lazy sometimes
    private String colorRgb;//lazy
    private int red, green, blue;

    public MyColor(int intColor) {
        // intColor must be 0xFFxxxxxx TODO
        this.intColor = intColor;
        this.colorRgb = null;
        blue = 0xFF & intColor; //get lower 8-1bit
        green = 0xFF & (intColor >>> 8); //get lower 16-9bit
        red = 0xFF & (intColor >>> 16); //get lower 24-17bit
    }

    MyColor(int red, int green, int blue) {
        // red/green/blue must be between 0 and 255 TODO
        this.intColor = 0;
        this.colorRgb = null;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getIntColor() {
        if(intColor == 0){
            intColor = 0xFF000000;
            intColor = intColor | (red << 16);
            intColor = intColor | (green << 8);
            intColor = intColor | blue;
        }
        return intColor;
    }

    public String getColorRgb() {
        if(colorRgb == null) {
            if(intColor == 0) getIntColor();
            int rgb = 0xFFFFFF & intColor; //filter lower 24bit
            colorRgb = "#" + String.format("%06X", rgb); //get "#RRGGBB" format
        }
        return colorRgb;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }
}
