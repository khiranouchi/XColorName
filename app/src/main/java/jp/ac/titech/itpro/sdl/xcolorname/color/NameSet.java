package jp.ac.titech.itpro.sdl.xcolorname.color;

import java.util.List;

/**
 * This class is to get all colors in some color nameset. <br>
 * To create new color nameset,
 * extend this class and override {@linkplain NameSet#getColors} to return all colors in the nameset.
 * @see MyNameColor
 */
public abstract class NameSet {
    /**
     * Get all colors in the nameset.
     * @return list of all colors
     */
    public abstract List<MyNameColor> getColors();
}
