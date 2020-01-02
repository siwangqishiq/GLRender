package com.xinlan.glrender;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.xinlan.yokirender.core.YokiCanvas;
import com.xinlan.yokirender.core.YokiPaint;
import com.xinlan.yokirender.core.YokiView;
import com.xinlan.yokirender.core.math.Color4f;

public class CustomView extends YokiView {
    private int viewWidth;
    private int viewHeight;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private YokiPaint mPaint;

    @Override
    public void onInit(int width, int height) {
        this.setRefreshColor(Color.RED);
        mPaint = new YokiPaint();
        mPaint.color = new Color4f(0.0f , 0.0f , 0.0f , 1.0f);
        mPaint.size = 11.0f;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean ret = super.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
//        int r, g , b = 255;
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                ret = true;
//                System.out.println("down = " + x +"    " +y);
//            case MotionEvent.ACTION_MOVE:
//                System.out.println("move" + x +"    " +y);
//                if(x >= 0){
//                    r =(int) ((x / getWidth()) * 255);
//                }else{
//                    r = 0;
//                }
//                if(y >= 0){
//                    g =(int) ((y / getHeight()) * 255);
//                }else{
//                    g = 0;
//                }
//
//                int bgColor = Color.rgb(r, g , b);
//                setRefreshColor(bgColor);
//                refreshView();
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//        }
        return ret;
    }

    @Override
    public void onRender(YokiCanvas canvas) {
        testPoint(canvas);
        //testLine(canvas);
    }

    private void testLine(YokiCanvas canvas) {

    }

    private void testPoint(YokiCanvas canvas) {
        mPaint.size = 10;
        mPaint.color.x = 1.0f;
        mPaint.color.y = 1.0f;
        mPaint.color.z = 0.0f;

//        canvas.drawPoint(100,100,mPaint);

        for(int i= 0 ; i<getHeight();i+=20){
            canvas.drawPoint(i,i,mPaint);
        }
    }
}
