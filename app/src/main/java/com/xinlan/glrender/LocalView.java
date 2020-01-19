package com.xinlan.glrender;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class LocalView extends View {
    Random mRnd = new Random();

    Paint mPaint = new Paint();

    public LocalView(Context context) {
        super(context);
        initView();
    }

    public LocalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LocalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public LocalView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        testCircle2(canvas);
        postInvalidate();
    }

    long lastTime = -1;
    private void testCircle2(Canvas canvas) {
        int left = getWidth();
        int top =getHeight();

        for(int i = 0 ; i < left ; i+=20){
            for(int j = 0 ; j < top ; j+=20) {
                mPaint.setColor(Color.argb(mRnd.nextInt(255) ,mRnd.nextInt(255) ,mRnd.nextInt(255) ,mRnd.nextInt(255)));
                canvas.drawCircle(i , j , 8, mPaint);
            }
        }//end for i
        long t = System.currentTimeMillis();
        long deltaTime = t - lastTime;
        System.out.println("render time = " + deltaTime);
        lastTime = t;
    }
}
