package com.xinlan.yokirender.core.command;

import android.opengl.GLES30;

import com.xinlan.yokirender.core.YokiPaint;
import com.xinlan.yokirender.core.math.Matrix3f;
import com.xinlan.yokirender.core.shader.ShaderManager;

import java.nio.FloatBuffer;

/**
 *  三角形图元渲染指令
 *
 */
public class TriangleCmd extends Cmd {
    public static final String RENDER_TRIANGLE = "_render_triangle";

    public static TriangleCmd newInstance() {
        TriangleCmd cmd = new TriangleCmd();
        cmd.init();
        return cmd;
    }

    private int mPosBufId;
    private int mColorBufId;

    protected FloatBuffer mPosBuf; //直线坐标数据
    protected FloatBuffer mColorBuf; //直线颜色

    protected int vertexCount(){
        return 3;
    }

    @Override
    public void init() {
        mProgramId = ShaderManager.getInstance().findProgramId(RENDER_TRIANGLE);
        mUniformMatrixLoc = GLES30.glGetUniformLocation(mProgramId , "uMatrix");

        final int vertexCount = vertexCount();
        //显存空间开辟2个缓冲区  一个存位置 一个存颜色
        int buffIds[] = new int[2];
        GLES30.glGenBuffers(2 , buffIds , 0);
        mPosBufId = buffIds[0];
        mPosBuf = allocateFloatBufBySize(vertexCount * 3);

        mColorBufId = buffIds[1];
        mColorBuf = allocateFloatBufBySize(vertexCount * 4);

        updateBuffer();
    }

    protected void updateBuffer() {
        final int vertexCount = vertexCount();

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER , mPosBufId);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER , vertexCount * 3 * Float.BYTES , mPosBuf , GLES30.GL_DYNAMIC_DRAW);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER , mColorBufId);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER ,  vertexCount * 4 * Float.BYTES , mColorBuf , GLES30.GL_DYNAMIC_DRAW);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER , 0);
    }

    public void update(float x1 , float y1 , float x2 , float y2 , float x3 , float y3 ,float zOrder, YokiPaint paint){
        mPosBuf.position(0);
        mPosBuf.put(x1);
        mPosBuf.put(y1);
        mPosBuf.put(zOrder);
        mPosBuf.put(x2);
        mPosBuf.put(y2);
        mPosBuf.put(zOrder);
        mPosBuf.put(x3);
        mPosBuf.put(y3);
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

    @Override
    public void render(Matrix3f matrix) {
        GLES30.glUseProgram(mProgramId);
        GLES30.glUniformMatrix3fv(mUniformMatrixLoc ,1 , true , matrix.getValues() , 0);

        GLES30.glEnableVertexAttribArray(0);
        GLES30.glEnableVertexAttribArray(1);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER  , mPosBufId);
        GLES30.glVertexAttribPointer(0 , 3 , GLES30.GL_FLOAT , false , 3 * Float.BYTES , 0);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER  , mColorBufId);
        GLES30.glVertexAttribPointer(1 , 4 , GLES30.GL_FLOAT , false ,  4 * Float.BYTES , 0);

        drawArrays();

        GLES30.glDisableVertexAttribArray(0);
        GLES30.glDisableVertexAttribArray(1);
    }

    protected void drawArrays(){
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES , 0 , 3);
    }
}//end class
