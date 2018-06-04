package jp.ac.titech.itpro.sdl.xcolorname;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

public class SquareView extends View {

    private boolean mAdjustWidth;

    public SquareView(Context context) {
        super(context, null);
    }

    public SquareView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mAdjustWidth = false;
        if(attrs != null){
            TypedArray attrsArray = context.obtainStyledAttributes(attrs, R.styleable.SquareView);
            mAdjustWidth = attrsArray.getBoolean(R.styleable.SquareView_adjust_width, false);
            attrsArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sideLength = 0;
        if(mAdjustWidth){
            sideLength = getMeasuredWidth();
        }else{
            sideLength = getMeasuredHeight();
        }
        setMeasuredDimension(sideLength, sideLength);
    }

    public void setAdjustWidth(boolean adjustWidth) {
        mAdjustWidth = adjustWidth;
    }
}
