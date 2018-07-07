package jp.ac.titech.itpro.sdl.xcolorname.color;

import android.graphics.Color;

/**
 * This class filterable {@linkplain MyColor}.
 * You can change color by filtering hue/saturation/value value of the color.
 */
public class MyFilterableColor extends MyColor {
    private float originalHue, originalSaturation, originalValue;
    private float curDeltaHue, curDeltaSaturation, curDeltaValue;

    public MyFilterableColor(int color) {
        super(color);

        getIntColor(); //update this.intColor
        float hsv[] = new float[3];
        Color.colorToHSV(this.intColor, hsv);
        originalHue = hsv[0];
        originalSaturation = hsv[1];
        originalValue = hsv[2];

        curDeltaHue = 0;
        curDeltaSaturation = 0;
        curDeltaValue = 0;
    }

    /**
     * Change hue value of the color.
     * Hue of color is between 0.0 and 360.0.
     * @param deltaHue amount of hue changed (negative number is possible)
     */
    public void filterHue(float deltaHue) {
        // Hue is between 0.0 and 360.0
        // deltaHue 1.0 <=> Hue 1.0

        curDeltaHue = deltaHue;

        float hsv[] = new float[3];
        hsv[0] = calcHue();
        hsv[1] = calcSaturation();
        hsv[2] = calcValue();

        this.intColor = Color.HSVToColor(hsv); //update this.intColor
        this.colorRgb = null;
        updateRedGreenBlue(); //this.red/green/blue = ...
    }

    /**
     * Change saturation value of the color.
     * Saturation of color is between 0.0 and 100.0.
     * @param deltaSaturation amount of saturation changed (negative number is possible)
     */
    public void filterSaturation(float deltaSaturation) {
        // Saturation is between 0.0 and 1.0
        // deltaSaturation 1.0 <=> Saturation 0.01

        curDeltaSaturation = deltaSaturation;

        float hsv[] = new float[3];
        hsv[0] = calcHue();
        hsv[1] = calcSaturation();
        hsv[2] = calcValue();

        this.intColor = Color.HSVToColor(hsv); //update this.intColor
        this.colorRgb = null;
        updateRedGreenBlue(); //this.red/green/blue = ...
    }

    /**
     * Change value value of the color.
     * Value of color is between 0.0 and 100.0.
     * @param deltaValue amount of value changed (negative number is possible)
     */
    public void filterValue(float deltaValue) {
        // Value is between 0.0 and 1.0
        // deltaValue 1.0 <=> Value 0.01

        curDeltaValue = deltaValue;

        float hsv[] = new float[3];
        hsv[0] = calcHue();
        hsv[1] = calcSaturation();
        hsv[2] = calcValue();

        this.intColor = Color.HSVToColor(hsv); //update this.intColor
        this.colorRgb = null;
        updateRedGreenBlue(); //this.red/green/blue = ...
    }

    private float calcHue() {
        float potenHue = originalHue + curDeltaHue;
        if(potenHue > 360){
            potenHue = 360;
        }else if(potenHue < 0){
            potenHue = 0;
        }
        return potenHue;
    }

    private float calcSaturation() {
        float potenSaturation = originalSaturation + curDeltaSaturation / 100;
        if(potenSaturation > 1){
            potenSaturation = 1;
        }else if(potenSaturation < 0){
            potenSaturation = 0;
        }
        return potenSaturation;
    }

    private float calcValue() {
        float potenValue = originalValue + curDeltaValue / 100;
        if(potenValue > 1){
            potenValue = 1;
        }else if(potenValue < 0){
            potenValue = 0;
        }
        return potenValue;
    }
}
