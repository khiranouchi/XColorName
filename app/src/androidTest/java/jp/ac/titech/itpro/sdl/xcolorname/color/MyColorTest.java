package jp.ac.titech.itpro.sdl.xcolorname.color;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MyColorTest {
    private MyColor mcFromRgb1, mcFromRgb2, mcFromRgb3;
    private MyColor mcFromInt1, mcFromInt2, mcFromInt3;

    @Before
    public void setUp() throws Exception {
        mcFromRgb1 = new MyColor(23, 34, 220); //0xFF1722DC
        mcFromRgb2 = new MyColor(0, 255, 127); //0xFF00FF7F
        mcFromRgb3 = new MyColor(0,0,0); //0xFF000000
        mcFromInt1 = new MyColor(0xFFFF0000); //(255,0,0)
        mcFromInt2 = new MyColor(0xFF0F3EFF); //(15,62,255)
        mcFromInt3 = new MyColor(0xFF000000); //(0,0,0)
    }

    @Test
    public void getIntColor1() {
        assertEquals(0xFF1722DC, mcFromRgb1.getIntColor());
    }

    @Test
    public void getIntColor2() {
        assertEquals(0xFF00FF7F, mcFromRgb2.getIntColor());
    }

    @Test
    public void getIntColor3() {
        assertEquals(0xFF000000, mcFromRgb3.getIntColor());
    }

    @Test
    public void getColorRgb1() {
        assertEquals("#1722DC", mcFromRgb1.getColorRgb());
    }

    @Test
    public void getColorRgb2() {
        assertEquals("#00FF7F", mcFromRgb2.getColorRgb());
    }

    @Test
    public void getColorRgb3() {
        assertEquals("#000000", mcFromRgb3.getColorRgb());
    }

    @Test
    public void getRedBlueGreen1() {
        assertEquals(255, mcFromInt1.getRed());
        assertEquals(0, mcFromInt1.getGreen());
        assertEquals(0, mcFromInt1.getBlue());
    }

    @Test
    public void getRedBlueGreen2() {
        assertEquals(15, mcFromInt2.getRed());
        assertEquals(62, mcFromInt2.getGreen());
        assertEquals(255, mcFromInt2.getBlue());
    }

    @Test
    public void getRedBlueGreen3() {
        assertEquals(0, mcFromInt3.getRed());
        assertEquals(0, mcFromInt3.getGreen());
        assertEquals(0, mcFromInt3.getBlue());
    }
}