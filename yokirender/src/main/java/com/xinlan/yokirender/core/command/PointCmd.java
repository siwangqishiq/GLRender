package com.xinlan.yokirender.core.command;

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

    public static PointCmd newInstance() {
        PointCmd cmd = new PointCmd();
        cmd.init();
        return cmd;
    }

    private FloatBuffer mPosBuf;
    private FloatBuffer mColorBuf;
    private FloatBuffer mSizeBuf;

    private float pos[] = new float[2];
    private float color[] = new float[4];
    private float size = 4.0f;

    private int mPosBufId;
    private int mColorBufId;
    private int mSizeBufId;

    public void init(){
        mProgramId = ShaderManager.getInstance().findProgramId(RENDER_POINT);
        mUniformMatrixLoc = GLES30.glGetUniformLocation(mProgramId , "uMatrix");

        int buffIds[] = new int[3];
        GLES30.glGenBuffers(3 , buffIds , 0);

        mPosBufId = buffIds[0];
        mPosBuf = OpenglEsUtils.allocateBuf(pos);
        mPosBuf.position(0);

        mColorBufId = buffIds[1];
        mColorBuf = OpenglEsUtils.allocateBuf(color);
        mColorBuf.position(0);

        mSizeBufId = buffIds[2];
        mSizeBuf = OpenglEsUtils.allocateBuf(size);
        mSizeBuf.position(0);

        updateBuffer();
    }

    private void updateBuffer() {
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER , mPosBufId);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER , pos.length * Float.BYTES , mPosBuf , GLES30.GL_DYNAMIC_DRAW);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER , mColorBufId);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER , color.length * Float.BYTES , mColorBuf , GLES30.GL_DYNAMIC_DRAW);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER , mSizeBufId);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER , Float.BYTES , mSizeBuf , GLES30.GL_DYNAMIC_DRAW);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER , 0);
    }

    public void update(float x , float y , YokiPaint paint) {
        mPosBuf.position(0);
        mPosBuf.put(x);
        mPosBuf.put(y);
        mPosBuf.position(0);

        paint.color.get(color);
        mColorBuf.position(0);
        mColorBuf.put(color);
        mColorBuf.position(0);

        mSizeBuf.position(0);
        mSizeBuf.put(paint.size);
        mSizeBuf.position(0);

        updateBuffer();
    }

    @Override
    public void render(Matrix3f matrix) {
        GLES30.glUseProgram(mProgramId);
        GLES30.glUniformMatrix3fv(mUniformMatrixLoc ,1 , false , matrix.getValues() , 0);

        GLES30.glEnableVertexAttribArray(0);
        GLES30.glEnableVertexAttribArray(1);
        GLES30.glEnableVertexAttribArray(2);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER  , mPosBufId);
        GLES30.glVertexAttribPointer(0 , 2 , GLES30.GL_FLOAT , false , 2 * Float.BYTES , 0);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER  , mColorBufId);
        GLES30.glVertexAttribPointer(1 , 4 , GLES30.GL_FLOAT , false ,  4 * Float.BYTES , 0);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER  , mSizeBufId);
        GLES30.glVertexAttribPointer(2 , 1 , GLES30.GL_FLOAT , false , Float.BYTES , 0);

        GLES30.glDrawArrays(GLES30.GL_POINTS , 0 , 1);

        GLES30.glDisableVertexAttribArray(0);
        GLES30.glDisableVertexAttribArray(1);
        GLES30.glDisableVertexAttribArray(2);
    }
} //end class
