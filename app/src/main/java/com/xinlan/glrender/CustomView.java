package com.xinlan.glrender;

import android.content.Context;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.xinlan.yokirender.core.YokiBit;
import com.xinlan.yokirender.core.YokiCanvas;
import com.xinlan.yokirender.core.YokiPaint;
import com.xinlan.yokirender.core.view.YokiView;
import com.xinlan.yokirender.math.Color4f;

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


    private RectF mSrcRect = new RectF();
    private RectF mDstRect = new RectF();
    private YokiBit mImage;

    private YokiBit mImage2;
    private YokiBit mBoomImage;

    @Override
    public void onInit(int width, int height) {
        this.setRefreshColor(Color.WHITE);
        mPaint = new YokiPaint();
        paintRed.color = new Color4f(1.0f, 0.0f, 0.0f, 1.0f);
        paintYellow.color = new Color4f(1.0f, 1.0f, 0.0f, 1.0f);
        mPaint.color = new Color4f(0.0f, 0.0f, 0.0f, 1.0f);
        mPaint.size = 11.0f;

        mImage = loadBit(R.drawable.gakki);
        mImage2 = loadBit(R.drawable.pic2);
        mBoomImage = loadBit(R.drawable.boom);
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

//        testBlend(canvas);
//        testSaveAndRestore(canvas);
//        testSaveAndRestore2(canvas);
//        testSaveAndRestore3(canvas);
//        testSaveAndRestore4(canvas);
//        testSaveAndRestore5(canvas);
//        testSaveAndRestore6(canvas);

//        testRect(canvas);
//        testRect2(canvas);
//        testRect3(canvas);

//        testCircle(canvas);
//        testCircle2(canvas);
//        testCircle3(canvas);
//        testCircle4(canvas);

//        testSprite1(canvas);
//        testSprite2(canvas);
//        testSprite3(canvas);
        testSprite4(canvas);
    }

    float offsetX = 0;
    float offsetDx = 1.5f;
    private void testSprite4(YokiCanvas canvas){
        float spriteWidth = mImage2.srcWidth / 2;

        mSrcRect.set(offsetX , 0 , offsetX + spriteWidth , mImage2.srcHeight);
        mDstRect.set(0 , getHeight() , getWidth() , getHeight() - mSrcRect.height());
        canvas.drawSprite(mImage2 , mSrcRect , mDstRect , null);

        offsetX+=offsetDx;
        if(offsetX >= mImage2.srcWidth / 2){
            offsetDx *= -1;
        }else if(offsetX <=0 ){
            offsetDx *= -1;
        }
    }

    private void testSprite3(YokiCanvas canvas){
        mSrcRect.set(mImage2.srcWidth/ 2 , 0 , mImage2.srcWidth , mImage2.srcHeight);
        float r = mImage2.srcWidth / mImage2.srcHeight;
        float bottom = getWidth() / r;
        mDstRect.set(0 , getHeight() , mSrcRect.width() , getHeight() - mSrcRect.height());
        canvas.drawSprite(mImage2 , mSrcRect , mDstRect , null);
    }

    private void testSprite1(YokiCanvas canvas){
        mSrcRect.set(0 , 0 , mImage.srcWidth  , mImage.srcHeight);
        float r = mImage.srcWidth / mImage.srcHeight;
        float bottom = getWidth() / r;
        mDstRect.set(0 , getHeight() , getWidth() , getHeight() - bottom);

        canvas.save();
        canvas.rotate(mDstRect.centerX() , mDstRect.centerY() , angle++);
        canvas.drawSprite(mImage , mSrcRect , mDstRect , null);
        canvas.restore();
    }

    int frame = 0;
    private void testSprite2(YokiCanvas canvas) {
        float spriteWidth = 132;
        float spriteHeight = 173;
        int x = frame % 5;
        int y = frame / 5;

        System.out.println("x , y = " + x +"  " + y);

        mSrcRect.set(x * spriteWidth , y * spriteHeight ,
                x * spriteWidth + spriteWidth , y * spriteHeight + spriteHeight);

        float spriteX = getWidth() / 2 - mSrcRect.width() / 2;
        float spriteY = getHeight() / 2 + mSrcRect.height() / 2;

        mDstRect.set(spriteX , spriteY, spriteX + mSrcRect.width() , spriteY - mSrcRect.height() );

        canvas.drawSprite(mBoomImage , mSrcRect , mDstRect , null);
        frame++;
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(frame >= 20) {
            frame = 0;
        }
    }

    @Override
    public void onDestory() {
        removeBit(mImage);
    }

    private void testCircle4(YokiCanvas canvas){
        int cube =10;
        for(int i = 0 ; i < 1000 ;i+=cube){
            for(int j = 0 ; j< 1000;j+=cube){
                mPaint.color.x = mRnd.nextFloat();
                mPaint.color.y = mRnd.nextFloat();
                mPaint.color.z = mRnd.nextFloat();
                mPaint.color.w = mRnd.nextFloat();
                canvas.drawCircle(i , j , 8, mPaint);
            }//end for j;
        }//end for i
    }

    private void testCircle2(YokiCanvas canvas) {
        int left = getWidth();
        int top =getHeight();

        for(int i = 0 ; i < left ; i+=20){
            for(int j = 0 ; j < top ; j+=20) {
                mPaint.color.x = mRnd.nextFloat();
                mPaint.color.y = mRnd.nextFloat();
                mPaint.color.z = mRnd.nextFloat();
                mPaint.color.w = mRnd.nextFloat();
                canvas.drawCircle(i , j , 8, mPaint);
            }
        }//end for i
    }


    private void testCircle(YokiCanvas canvas) {
        float x = getWidth() / 2;
        float y = getHeight() / 2;

        mPaint.color.x = 0.0f;
        mPaint.color.y = 1.0f;
        mPaint.color.z = 0.0f;
        mPaint.color.w = 1.0f;

        canvas.drawCircle(x , y , 200 , mPaint);
    }


    private void testCircle3(YokiCanvas canvas) {
        float x = getWidth() / 2;
        float y = getHeight() / 2;

        mPaint.color.x = 1.0f;
        mPaint.color.y = 1.0f;
        mPaint.color.z = 0.0f;
        mPaint.color.w = 1.0f;

        canvas.drawCircle(x , y , 200 , mPaint);

        mPaint.color.x = 1.0f;
        mPaint.color.y = 0.0f;
        mPaint.color.z = 0.0f;
        mPaint.color.w = 0.2f;
        canvas.drawCircle(x+200 , y + 200 , 200 , mPaint);
    }

    int angle = 0;
    private void testRect3(YokiCanvas canvas){
        mPaint.color.x = 0.0f;
        mPaint.color.y = 1.0f;
        mPaint.color.z = 0.0f;
        mPaint.color.w = 0.5f;

        canvas.save();
        canvas.rotate(100 + 200 , 500 - 200 , angle++);
        canvas.drawRect(100 , 500 , 400 , 400 , mPaint);
        canvas.restore();

        mPaint.color.x = 1.0f;
        mPaint.color.y = 1.0f;
        mPaint.color.z = 1.0f;
        mPaint.color.w = 1.0f;
        canvas.drawRect(0 , 1000 , 400 , 400 , mPaint);
    }


    private void testRect2(YokiCanvas canvas){
        mPaint.color.x = mRnd.nextFloat();
        mPaint.color.y = mRnd.nextFloat();
        mPaint.color.z = mRnd.nextFloat();
        mPaint.color.w = mRnd.nextFloat();
        canvas.drawRect(100 , 500 , 400 , 400 , mPaint);
    }


    private void testRect(YokiCanvas canvas){
        int cube = 10;
        for(int i = 0 ; i < 1000 ;i+=cube){
            for(int j = 0 ; j< 1000;j+=cube){
                mPaint.color.x = mRnd.nextFloat();
                mPaint.color.y = mRnd.nextFloat();
                mPaint.color.z = mRnd.nextFloat();
                mPaint.color.w = mRnd.nextFloat();
                canvas.drawRect(i , j , cube , cube , mPaint);
            }//end for j;
        }//end for i
    }

    private void testSaveAndRestore6(YokiCanvas canvas) {
        float center_x = 0;
        float center_y = 0;

        mPaint.color.x = 1.0f;
        mPaint.color.y = 0.0f;
        mPaint.color.z = 1.0f;
        mPaint.color.w = 1.0f;
        mPaint.size = 20;

        int triangleWidth = 200;

        canvas.drawTriangle(center_x,center_y,center_x + triangleWidth,center_y,center_x,center_y +triangleWidth , mPaint);
        canvas.save();
        mPaint.color.x = 0.0f;
        mPaint.color.y = 0.0f;
        mPaint.color.z = 1.0f;
        mPaint.color.w = 0.2f;
        canvas.scale(0.5f , 0.8f);
        canvas.drawTriangle(center_x,center_y,center_x + triangleWidth,center_y,center_x,center_y +triangleWidth , mPaint);
        canvas.restore();
    }

    private void testSaveAndRestore5(YokiCanvas canvas) {
        float center_x = getWidth() / 2;
        float center_y = getHeight() / 2;

        mPaint.color.x = 1.0f;
        mPaint.color.y = 0.0f;
        mPaint.color.z = 1.0f;
        mPaint.color.w = 1.0f;
        mPaint.size = 20;

        canvas.drawTriangle(center_x,center_y,center_x + 200,center_y,center_x,center_y +200 , mPaint);
        canvas.save();
        canvas.rotate(center_x,center_y ,45);
        mPaint.color.x = 1.0f;
        mPaint.color.y = 1.0f;
        mPaint.color.z = 1.0f;
        mPaint.color.w = 1.0f;
        canvas.drawTriangle(center_x,center_y,center_x + 200,center_y,center_x,center_y +200 , mPaint);
        canvas.restore();



        canvas.save();
        canvas.translate(center_x , center_y);

        canvas.save();
        canvas.rotate(center_x ,center_y , 10);
        for(int i = 0 ; i < 500 ; i ++){
            mPaint.color.x = mRnd.nextFloat();
            mPaint.color.y = mRnd.nextFloat();
            mPaint.color.z = 1.0f;

            canvas.drawPoint(i , 0, mPaint);
        }
        canvas.restore();

        for(int i = 0 ; i < 500 ; i ++){
            mPaint.color.x = mRnd.nextFloat();
            mPaint.color.y = mRnd.nextFloat();
            mPaint.color.z = 1.0f;

            canvas.drawPoint(i , 0, mPaint);
        }

        canvas.restore();
    }



    int rotateAngle;
    private void testSaveAndRestore4(YokiCanvas canvas) {
        float center_x = getWidth() / 2;
        float center_y = getHeight() / 2;

        mPaint.color.x = 1.0f;
        mPaint.color.y = 0.0f;
        mPaint.color.z = 0.0f;
        mPaint.color.w = 1.0f;
        canvas.drawTriangle(center_x,center_y,center_x + 200,center_y,center_x,center_y +200 , mPaint);

        mPaint.color.x = 1.0f;
        mPaint.color.y = 1.0f;
        mPaint.color.z = 0.0f;
        mPaint.color.w = 1.0f;

        canvas.save();
        canvas.rotate(center_x , center_y , rotateAngle);
        rotateAngle++;
        canvas.drawTriangle(center_x,center_y,center_x + 200,center_y,center_x,center_y +200 , mPaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(rotateAngle);
        canvas.drawTriangle(0,0,0 + 200,0,0,0 +200 , mPaint);
        canvas.restore();
    }

    private void testSaveAndRestore3(YokiCanvas canvas) {
        mPaint.color.x = 1.0f;
        mPaint.color.y = 0.0f;
        mPaint.color.z = 0.0f;
        mPaint.color.w = 1.0f;
        canvas.save();
        canvas.translate(200,200);
        canvas.save();
        canvas.translate(200,200);
        canvas.drawTriangle(0,0,200,0,0,200 , mPaint);
        canvas.restore();

        mPaint.color.x = 1.0f;
        mPaint.color.y = 1.0f;
        mPaint.color.z = 0.0f;
        mPaint.color.w = 1.0f;
        canvas.drawTriangle(0,0,200,0,0,200 , mPaint);
        canvas.restore();
    }


    private void testSaveAndRestore2(YokiCanvas canvas) {
        mPaint.color.x = 1.0f;
        mPaint.color.y = 0.0f;
        mPaint.color.z = 0.0f;
        mPaint.color.w = 1.0f;

        canvas.drawTriangle(0,0,200,0,0,200 , mPaint);
        canvas.translate(0 , 400);
        canvas.drawTriangle(0,0,200,0,0,200 , mPaint);
        canvas.translate(0 , -400);

        canvas.save();
        mPaint.color.x = 1.0f;
        mPaint.color.y = 1.0f;
        mPaint.color.z = 0.0f;
        mPaint.color.w = 1.0f;
        canvas.translate(200,0);
        canvas.drawTriangle(0,0,200,0,0,200 , mPaint);
        canvas.translate(200,0);
        canvas.drawTriangle(0,0,200,0,0,200 , mPaint);
        canvas.restore();
    }


    private void testSaveAndRestore(YokiCanvas canvas) {
        mPaint.color.x = 1.0f;
        mPaint.color.y = 0.0f;
        mPaint.color.z = 0.0f;
        mPaint.color.w = 1.0f;
        mPaint.size = 20.0f;

        float center_x = getWidth() / 2;
        float center_y = getHeight() / 2;

        canvas.drawPoint(center_x , center_y , mPaint);

        canvas.save();
        canvas.translate(100 , 100);
        canvas.drawPoint(center_x , center_y , mPaint);
        canvas.drawPoint(0, 0 , mPaint);
        canvas.restore();

        canvas.drawPoint(0, 0 , mPaint);
    }

    float c_x = 20;
    float c_y = 20;

    private void testBlend(YokiCanvas canvas) {
        testPoints3(canvas);
        drawTriangles(canvas);
        testPoint2(c_x, c_y, canvas);

        c_x += mRnd.nextFloat();
        c_y += mRnd.nextFloat();
    }

    private void testTriangles2(YokiCanvas canvas) {
        for (int j = 0; j < 2000; j += 20) {
            for (int i = 0; i < 2000; i += 20) {
                mPaint.color.x = mRnd.nextFloat();
                mPaint.color.y = mRnd.nextFloat();
                mPaint.color.z = mRnd.nextFloat();
                mPaint.color.w = 1.0f;
                canvas.drawTriangle(i, j, i + 20, j, i + 10, j + 20, mPaint);
            }//end for i
        }//end for j
    }

    private void testPoints3(YokiCanvas canvas) {
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 2000; i += 20) {
            for (int j = 0; j < getHeight(); j += 20) {
                mPaint.color.x = mRnd.nextFloat();
                mPaint.color.y = mRnd.nextFloat();
                mPaint.color.z = mRnd.nextFloat();
                mPaint.color.w = 1.0f;
                mPaint.size = mRnd.nextInt(20);

                float x = 0 + i;
                float y = 0 + j;
                canvas.drawPoint(x, y, mPaint);
            }
        }
        long t2 = System.currentTimeMillis();
        System.out.println("delta = " + (t2 - t1));
    }

    private void drawTestDepth2(YokiCanvas canvas) {
        paintRed.size = 8f;
        canvas.drawLine(100, 100, 700, 700, paintRed);
        canvas.drawRect(400, 500, 200, 200, paintYellow);
        //canvas.drawRect(100,200,200,200 , paintYellow);
    }


    private void drawTestDepth(YokiCanvas canvas) {
        YokiPaint paint2 = new YokiPaint();
        paint2.color = new Color4f(1.0f, 0.0f, 0.0f, 1.0f);
        canvas.drawRect(400, 400, 200, 200, paint2);

        YokiPaint paint = new YokiPaint();
        paint.color = new Color4f(1.0f, 1.0f, 0.0f, 1.0f);
        canvas.drawTriangle(200, 200, 500, 200, 400, 500, paint);
    }

    private void drawRects(YokiCanvas canvas) {
        YokiPaint paint = new YokiPaint();
        paint.color = new Color4f(1.0f, 1.0f, 0.0f, 1.0f);
        canvas.drawRect(100, 100, 100, 100, paint);
    }

    private void drawRects2(YokiCanvas canvas) {
        YokiPaint paint = new YokiPaint();
        for (int i = 0; i < 2000; i += 20) {
            for (int j = 0; j < 2000; j += 20) {
                paint.color.x = (float) j / 2000;
                paint.color.y = (float) i / 2000;
                paint.color.z = 0f;
                paint.color.w = 1.0f;
                canvas.drawRect(i, j, 20, 20, paint);
            }
        }
    }


    private void drawTriangles(YokiCanvas canvas) {
        for (int j = 0; j < 2000; j += 20) {
            for (int i = 0; i < 2000; i += 20) {
                mPaint.color = new Color4f(1.0f, (float) j / 2000, (float) i / 2000, 1.0f);
                canvas.drawTriangle(i, j, i + 20, j, i + 10, j + 30, mPaint);
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

    private void testPoint2(float c_x, float c_y, YokiCanvas canvas) {
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
