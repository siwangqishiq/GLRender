package com.xinlan.yokirender.core.command;

import android.opengl.GLES30;

import com.xinlan.yokirender.core.YokiPaint;
import com.xinlan.yokirender.math.Matrix3f;
import com.xinlan.yokirender.core.shader.ShaderManager;

import java.nio.FloatBuffer;

/**
 *  三角形图元渲染指令
 *
 */
public class TriangleCmd extends Cmd {
    public static final String RENDER_TRIANGLE = "_render_triangle";

    public final int ELEMENT_COUNT = 1024; //渲染指令可渲染元素数量
    public final int vertexPerElement = 3;

    public static TriangleCmd newInstance() {
        TriangleCmd cmd = new TriangleCmd();
        cmd.init();
        return cmd;
    }


    @Override
    public void init() {
        mProgramId = ShaderManager.getInstance().findProgramId(RENDER_TRIANGLE);
        mUniformMatrixLoc = GLES30.glGetUniformLocation(mProgramId , "uMatrix");

        //显存空间开辟2个缓冲区  一个存位置 一个存颜色
        int buffIds[] = new int[2];
        GLES30.glGenBuffers(2 , buffIds , 0);
        mPosBufId = buffIds[0];
        mPosBuf = allocateFloatBufBySize(ELEMENT_COUNT * vertexPerElement * 3);

        mColorBufId = buffIds[1];
        mColorBuf = allocateFloatBufBySize(ELEMENT_COUNT * vertexPerElement * 4);
    }

    protected void updateBuffer() {
        final int vertextCount = mIndex;

        mPosBuf.position(0);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER , mPosBufId);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER ,
                vertextCount * vertexPerElement * 3 * Float.BYTES ,
                mPosBuf , GLES30.GL_DYNAMIC_DRAW);

        mColorBuf.position(0);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER , mColorBufId);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER ,
                vertextCount * vertexPerElement * 4 * Float.BYTES ,
                mColorBuf , GLES30.GL_DYNAMIC_DRAW);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER , 0);
    }

    public void appendRender(float x1 , float y1 , float x2 , float y2 , float x3 , float y3 ,float zOrder, YokiPaint paint) {
        mPosBuf.put(x1);
        mPosBuf.put(y1);
        mPosBuf.put(zOrder);
        mPosBuf.put(x2);
        mPosBuf.put(y2);
        mPosBuf.put(zOrder);
        mPosBuf.put(x3);
        mPosBuf.put(y3);
        mPosBuf.put(zOrder);

        for (int i = 0, count = vertexPerElement; i < count; i++) {
            mColorBuf.put(paint.color.x);
            mColorBuf.put(paint.color.y);
            mColorBuf.put(paint.color.z);
            mColorBuf.put(paint.color.w);
        }//end for i

        increaseIndex(ELEMENT_COUNT);
    }

    @Override
    public void render(Matrix3f matrix) {
        updateBuffer();

        GLES30.glUseProgram(mProgramId);
        GLES30.glUniformMatrix3fv(mUniformMatrixLoc ,1 , true , matrix.getValues() , 0);

        GLES30.glEnableVertexAttribArray(0);
        GLES30.glEnableVertexAttribArray(1);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER  , mPosBufId);
        GLES30.glVertexAttribPointer(0 , 3 , GLES30.GL_FLOAT , false , 3 * Float.BYTES , 0);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER  , mColorBufId);
        GLES30.glVertexAttribPointer(1 , 4 , GLES30.GL_FLOAT , false ,  4 * Float.BYTES , 0);

        //drawArrays();
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES , 0 , 3 * mIndex);

        GLES30.glDisableVertexAttribArray(0);
        GLES30.glDisableVertexAttribArray(1);
    }

    protected void drawArrays(){

    }

    @Override
    public void reset() {
        mPosBuf.position(0);
        mColorBuf.position(0);
        super.reset();
    }
}//end class
