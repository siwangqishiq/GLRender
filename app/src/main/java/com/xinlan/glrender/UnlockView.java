package com.xinlan.glrender;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.xinlan.yokirender.core.YokiCanvas;
import com.xinlan.yokirender.core.YokiView;

public class UnlockView extends YokiView {
    private int viewWidth;
    private int viewHeight;

    public UnlockView(Context context) {
        super(context);
    }

    public UnlockView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onInit(int width, int height) {
        this.setRefreshColor(Color.YELLOW);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean ret = super.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                ret = true;
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return ret;
    }

    @Override
    public void onRender(YokiCanvas canvas) {

    }
}
