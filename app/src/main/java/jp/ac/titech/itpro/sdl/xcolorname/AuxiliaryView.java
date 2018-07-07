package jp.ac.titech.itpro.sdl.xcolorname;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * This is view which displays auxiliary cross mark useful for the center of camera preview.
 * eg, add this and {@linkplain CameraView} to the same {@linkplain android.widget.FrameLayout}.
 */
public class AuxiliaryView extends View {
    private static final int WIDTH = 5;
    private static final int LENGTH_HALF = 50;
    private static final int COLOR = Color.WHITE;

    private int x;
    private int y;
    private Paint paint;

    public AuxiliaryView(Context context, int x, int y) {
        super(context);
        this.x = x;
        this.y = y;
        paint = new Paint();
        paint.setColor(COLOR);
        paint.setStrokeWidth(WIDTH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(x - LENGTH_HALF, y, x + LENGTH_HALF, y, paint);
        canvas.drawLine(x, y - LENGTH_HALF, x, y + LENGTH_HALF, paint);
    }
}
