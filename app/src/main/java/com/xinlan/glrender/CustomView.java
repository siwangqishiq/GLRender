package com.xinlan.glrender;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.xinlan.yokirender.core.YokiCanvas;
import com.xinlan.yokirender.core.YokiPaint;
import com.xinlan.yokirender.core.view.YokiView;
import com.xinlan.yokirender.core.math.Color4f;

import java.util.Random;

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

    YokiPaint paintRed = new YokiPaint();

    YokiPaint paintYellow = new YokiPaint();

    Random mRnd = new Random();

    @Override
    public void onInit(int width, int height) {
        this.setRefreshColor(Color.BLACK);
        mPaint = new YokiPaint();
        paintRed.color = new Color4f(1.0f , 0.0f , 0.0f , 1.0f);
        paintYellow.color = new Color4f(1.0f , 1.0f , 0.0f , 1.0f);
        mPaint.color = new Color4f(0.0f, 0.0f, 0.0f, 1.0f);
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

    float c_x = 20;
    float c_y = 20;

    @Override
    public void onRender(YokiCanvas canvas) {
//        testPoint(canvas);
//        testPoint2(canvas);
//        testPoints3(canvas);
//
//        testLine(canvas);
//        testLine2(canvas);
//        testTriangles2(canvas);
//        drawTriangles(canvas);
//        testPoint2(c_x , c_y , canvas);

//        drawRects(canvas);
//        drawRects2(canvas);
//
//        drawTestDepth(canvas);
//        drawTestDepth2(canvas);

        testBlend(canvas);
    }

    private void testBlend(YokiCanvas canvas){
        testPoints3(canvas);
        drawTriangles(canvas);
        testPoint2(c_x , c_y , canvas);

        c_x += mRnd.nextFloat();
        c_y += mRnd.nextFloat();
    }

    private void testTriangles2(YokiCanvas canvas) {
        for(int j = 0 ; j < 2000 ; j +=20){
            for(int i = 0 ; i < 2000;i+=20){
                mPaint.color.x = mRnd.nextFloat();
                mPaint.color.y = mRnd.nextFloat();
                mPaint.color.z = mRnd.nextFloat();
                mPaint.color.w = 1.0f;
                canvas.drawTriangle(i,j,i+20,j,i+10,j +20, mPaint);
            }//end for i
        }//end for j
    }

    private void testPoints3(YokiCanvas canvas) {
        long t1 = System.currentTimeMillis();
        for(int i = 0 ; i < 2000 ; i+=20){
            for(int j = 0 ; j< getHeight() ; j+=20){
                mPaint.color.x = mRnd.nextFloat();
                mPaint.color.y = mRnd.nextFloat();
                mPaint.color.z = mRnd.nextFloat();
                mPaint.color.w = 1.0f;
                mPaint.size = mRnd.nextInt(20);

                float x = 0 + i;
                float y = 0  + j;
                canvas.drawPoint(x, y, mPaint);
            }
        }
        long t2 = System.currentTimeMillis();
        System.out.println("delta = " + (t2 - t1));
    }

    private void drawTestDepth2(YokiCanvas canvas) {
        paintRed.size=8f;
        canvas.drawLine(100,100,700,700 , paintRed);
        canvas.drawRect(400,500,200,200 , paintYellow);
        //canvas.drawRect(100,200,200,200 , paintYellow);
    }


    private void drawTestDepth(YokiCanvas canvas){
        YokiPaint paint2 = new YokiPaint();
        paint2.color = new Color4f(1.0f, 0.0f, 0.0f, 1.0f);
        canvas.drawRect(400 , 400 , 200 , 200 , paint2);

        YokiPaint paint = new YokiPaint();
        paint.color = new Color4f(1.0f, 1.0f, 0.0f, 1.0f);
        canvas.drawTriangle(200,200 , 500 ,200 , 400 , 500 , paint);
    }

    private void drawRects(YokiCanvas canvas) {
        YokiPaint paint = new YokiPaint();
        paint.color = new Color4f(1.0f, 1.0f, 0.0f, 1.0f);
       canvas.drawRect(100,100,100,100 , paint);
    }

    private void drawRects2(YokiCanvas canvas) {
        YokiPaint paint = new YokiPaint();
        for(int i = 0 ; i < 2000 ; i+= 20){
            for(int j = 0 ; j < 2000 ; j+=20){
                paint.color.x = (float)j / 2000;
                paint.color.y = (float)i / 2000;
                paint.color.z =0f;
                paint.color.w = 1.0f;
                canvas.drawRect(i,j,20,20 , paint);
            }
        }
    }


    private void drawTriangles(YokiCanvas canvas) {
        for(int j = 0 ; j < 2000 ; j +=20){
            for(int i = 0 ; i < 2000;i+=20){
                mPaint.color = new Color4f(1.0f, (float)j / 2000, (float)i / 2000, 1.0f);
                canvas.drawTriangle(i,j,i+20,j,i+10,j +30, mPaint);
            }//end for i
        }//end for j
    }


    private void testLine2(YokiCanvas canvas) {
        YokiPaint paint = new YokiPaint();
        paint.color = new Color4f(1.0f, 1.0f, 1.0f, 1.0f);
        paint.size = 18.0f;

        canvas.drawLine(0, 0, 800, 600, paint);
        canvas.drawLine(0, 600, 800, 0, paint);
    }

    private void testLine(YokiCanvas canvas) {
        float c_x = 400;
        float c_y = 400;

        float radius = 200;
        for (int i = 0; i < 360; i++) {
            mPaint.color.x = mRnd.nextFloat();
            mPaint.color.y = mRnd.nextFloat();
            mPaint.color.z = mRnd.nextFloat();
            mPaint.color.w = 1.0f;
            float x = (float) (c_x + radius * Math.cos(i));
            float y = (float) (c_y + radius * Math.sin(i));
            canvas.drawLine(c_x, c_y, x, y, mPaint);
        }
    }

    private void testPoint(YokiCanvas canvas) {
        mPaint.size = 10;
        mPaint.color.x = 1.0f;
        mPaint.color.y = 1.0f;
        mPaint.color.z = 0.0f;

//        canvas.drawPoint(100,100,mPaint);

        for (int i = 0; i < getHeight(); i += 20) {
            canvas.drawPoint(i, i, mPaint);
        }
    }

    private void testPoint2(float c_x ,float c_y , YokiCanvas canvas) {
        mPaint.size = 10;
        mPaint.color.x = 1.0f;
        mPaint.color.y = 1.0f;
        mPaint.color.z = 0.0f;

        float radius = 200;
        for (int i = 0; i < 360; i++) {
            float x = (float) (c_x + radius * Math.cos(i));
            float y = (float) (c_y + radius * Math.sin(i));
            canvas.drawPoint(x, y, mPaint);
        }
    }
}
