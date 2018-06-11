package jp.ac.titech.itpro.sdl.xcolorname.color;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;

/**
 * This test is collect only when poten colors like
 *  potenColors.add(new MyNameColor("red", 255, 0, 0));
 *  potenColors.add(new MyNameColor("green", 0, 255, 0));
 *  potenColors.add(new MyNameColor("blue", 0, 0, 255));
 *  potenColors.add(new MyNameColor("magenta", 255, 0, 255));
 *  potenColors.add(new MyNameColor("cyan", 0,255, 255));
 *  potenColors.add(new MyNameColor("yellow", 255, 255, 0));
 *  potenColors.add(new MyNameColor("black", 0,0,0));
 *  potenColors.add(new MyNameColor("white", 255, 255, 255));
 */
public class RgbrColorSimilarityTest {
    private MyColor mc1, mc2;
    private RgbrColorSimilarity rcs;
    private List<MyNameColor> similarColors;

    @Before
    public void setUp() throws Exception {
        rcs = new RgbrColorSimilarity();
        similarColors = new ArrayList<>();
        mc1 = new MyColor(244, 0, 0);
        mc2 = new MyColor(127, 64, 127);
    }

    @Test
    public void getSimilarColor1() {
        similarColors = rcs.getSimilarColor(mc1);

        // map MyNameColor to colorName
        List<String> colorName = new ArrayList<>();
        for(MyNameColor similarColor: similarColors)
            colorName.add(similarColor.getColorName());

        assertThat(colorName, contains("red","black","magenta","yellow","blue","green","white","cyan"));
    }

    @Test
    public void getSimilarColor2() {
        similarColors = rcs.getSimilarColor(mc2);

        // map MyNameColor to colorName
        List<String> colorName = new ArrayList<>();
        for(MyNameColor similarColor: similarColors)
            colorName.add(similarColor.getColorName());

        assertThat(colorName, contains("black","red","blue","magenta","green","yellow","cyan","white"));
    }
}