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

        int r, g , b = 255;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                ret = true;
                System.out.println("down = " + x +"    " +y);
            case MotionEvent.ACTION_MOVE:
                System.out.println("move" + x +"    " +y);
                if(x >= 0){
                    r =(int) ((x / getWidth()) * 255);
                }else{
                    r = 0;
                }
                if(y >= 0){
                    g =(int) ((y / getHeight()) * 255);
                }else{
                    g = 0;
                }

                int bgColor = Color.rgb(r, g , b);
                setRefreshColor(bgColor);
                refreshView();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return ret;
    }

    @Override
    public void onRender(YokiCanvas canvas) {
        //canvas.drawPoint(100,100 , );
    }
}
