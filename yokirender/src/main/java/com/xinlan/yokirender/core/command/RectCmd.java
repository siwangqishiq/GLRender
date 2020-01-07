package com.xinlan.yokirender.core.command;

import android.opengl.GLES30;

import com.xinlan.yokirender.core.YokiPaint;

/**
 *   矩形绘制指令
 */
public class RectCmd extends TriangleCmd {
    public static RectCmd newInstance() {
        RectCmd cmd = new RectCmd();
        cmd.init();
        return cmd;
    }

    protected int vertexCount(){
        return 4;
    }

    public void update(float left , float top , float width , float height ,float zOrder, YokiPaint paint){
        mPosBuf.position(0);
        mPosBuf.put(left);
        mPosBuf.put(top);
        mPosBuf.put(zOrder);
        mPosBuf.put(left + width);
        mPosBuf.put(top);
        mPosBuf.put(zOrder);
        mPosBuf.put(left + width);
        mPosBuf.put(top - height);
        mPosBuf.put(zOrder);
        mPosBuf.put(left);
        mPosBuf.put(top - height);
        mPosBuf.put(zOrder);
        mPosBuf.position(0);

        mColorBuf.position(0);
        for(int i = 0 , count = vertexCount() ; i <count ;i++){
            mColorBuf.put(paint.color.x);
            mColorBuf.put(paint.color.y);
            mColorBuf.put(paint.color.z);
            mColorBuf.put(paint.color.w);
        }//end for i
        mColorBuf.position(0);

        updateBuffer();
    }

    protected void drawArrays(){
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN , 0 , vertexCount());
    }
}//end class
