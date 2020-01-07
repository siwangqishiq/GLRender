package com.xinlan.yokirender.core.command;

import android.icu.text.SymbolTable;
import android.opengl.GLES30;

import com.xinlan.yokirender.core.YokiPaint;
import com.xinlan.yokirender.core.math.Matrix3f;
import com.xinlan.yokirender.core.shader.ShaderManager;
import com.xinlan.yokirender.util.OpenglEsUtils;

import java.nio.FloatBuffer;

/**
 * 点 render 指令
 */
public class PointCmd extends Cmd {
    public static final String RENDER_POINT = "_render_point";

    public final int ELEMENT_COUNT = 2048; //渲染指令可渲染顶点数量

    public static PointCmd newInstance() {
        PointCmd cmd = new PointCmd();
        cmd.init();
        return cmd;
    }

    private FloatBuffer mPosBuf;
    private FloatBuffer mColorBuf;
    private FloatBuffer mSizeBuf;

//    private float pos[] = new float[3];
//    private float color[] = new float[4];
//    private float size = 4.0f;

    private int mPosBufId;
    private int mColorBufId;
    private int mSizeBufId;

    public void init(){
        mIndex = 0;

        mProgramId = ShaderManager.getInstance().findProgramId(RENDER_POINT);
        mUniformMatrixLoc = GLES30.glGetUniformLocation(mProgramId , "uMatrix");

        int buffIds[] = new int[3];
        GLES30.glGenBuffers(3 , buffIds , 0);

        mPosBufId = buffIds[0];
        mPosBuf = allocateFloatBufBySize(3 * ELEMENT_COUNT);
        mPosBuf.position(0);

        mColorBufId = buffIds[1];
        mColorBuf = allocateFloatBufBySize(4 * ELEMENT_COUNT);
        mColorBuf.position(0);

        mSizeBufId = buffIds[2];
        mSizeBuf = allocateFloatBufBySize(ELEMENT_COUNT);
        mSizeBuf.position(0);

        reset();
    }

    private void updateBuffer() {
        final int vertextCount = mIndex;

        mPosBuf.position(0);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER , mPosBufId);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER , vertextCount * 3 * Float.BYTES , mPosBuf , GLES30.GL_DYNAMIC_DRAW);

        mColorBuf.position(0);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER , mColorBufId);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER , vertextCount * 4 * Float.BYTES , mColorBuf , GLES30.GL_DYNAMIC_DRAW);

        mSizeBuf.position(0);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER , mSizeBufId);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER , vertextCount * Float.BYTES , mSizeBuf , GLES30.GL_DYNAMIC_DRAW);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER , 0);
    }

    public void appendRender(float x , float y ,float zOrder , YokiPaint paint) {
        mPosBuf.put(x);
        mPosBuf.put(y);
        mPosBuf.put(zOrder);

        //mColorBuf.put(color);
        mColorBuf.put(paint.color.x);
        mColorBuf.put(paint.color.y);
        mColorBuf.put(paint.color.z);
        mColorBuf.put(paint.color.w);

        mSizeBuf.put(paint.size);

        mIndex++;

        if(mIndex >= ELEMENT_COUNT){
            isFull = true;
        }
    }

    @Override
    public void render(Matrix3f matrix) {
        updateBuffer();

        GLES30.glUseProgram(mProgramId);
        GLES30.glUniformMatrix3fv(mUniformMatrixLoc ,1 , true , matrix.getValues() , 0);

        GLES30.glEnableVertexAttribArray(0);
        GLES30.glEnableVertexAttribArray(1);
        GLES30.glEnableVertexAttribArray(2);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER  , mPosBufId);
        GLES30.glVertexAttribPointer(0 , 3 , GLES30.GL_FLOAT , false , 3 * Float.BYTES , 0);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER  , mColorBufId);
        GLES30.glVertexAttribPointer(1 , 4 , GLES30.GL_FLOAT , false ,  4 * Float.BYTES , 0);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER  , mSizeBufId);
        GLES30.glVertexAttribPointer(2 , 1 , GLES30.GL_FLOAT , false , Float.BYTES , 0);

        GLES30.glDrawArrays(GLES30.GL_POINTS , 0 , mIndex + 1);

        GLES30.glDisableVertexAttribArray(0);
        GLES30.glDisableVertexAttribArray(1);
        GLES30.glDisableVertexAttribArray(2);
    }

    @Override
    public void reset() {
        mPosBuf.position(0);
        super.reset();
    }
} //end class
