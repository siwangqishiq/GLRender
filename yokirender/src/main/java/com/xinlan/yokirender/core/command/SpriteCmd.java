package com.xinlan.yokirender.core.command;

import android.opengl.GLES20;
import android.opengl.GLES30;

import com.xinlan.yokirender.core.YokiPaint;
import com.xinlan.yokirender.core.shader.ShaderManager;
import com.xinlan.yokirender.math.Matrix3f;

import java.nio.FloatBuffer;

/**
 *  渲染 2D位图指令
 *
 */
public class SpriteCmd extends Cmd {
    public static final String RENDER_SPRITE = "_render_sprite";

    public static SpriteCmd newInstance() {
        SpriteCmd cmd = new SpriteCmd();
        cmd.init();
        return cmd;
    }

    //顶点坐标
    private FloatBuffer mPosBuf;
    //纹理坐标
    private FloatBuffer mUvBuf;

    private int mTextureId;

    @Override
    public void init() {
        mProgramId = ShaderManager.getInstance().findProgramId(RENDER_SPRITE);
        mUniformMatrixLoc = GLES30.glGetUniformLocation(mProgramId , "uMatrix");

        mPosBuf = allocateFloatBufBySize(4 * 3);
        mUvBuf = allocateFloatBufBySize( 4 * 2);
    }

    public void appendRender(
            int textureId,
            float _x1, float _y1,
            float _x2, float _y2,
            float _x3, float _y3,
            float _x4, float _y4,float zOrder,
            float uvX1 , float uvY1,
            float uvX2 , float uvY2,
            float uvX3 , float uvY3,
            float uvX4 , float uvY4,
            YokiPaint paint){
        mTextureId = textureId;

        mPosBuf.position(0);
        mPosBuf.put(_x1);
        mPosBuf.put(_y1);
        mPosBuf.put(zOrder);
        mPosBuf.put(_x2);
        mPosBuf.put(_y2);
        mPosBuf.put(zOrder);
        mPosBuf.put(_x3);
        mPosBuf.put(_y3);
        mPosBuf.put(zOrder);
        mPosBuf.put(_x4);
        mPosBuf.put(_y4);
        mPosBuf.put(zOrder);
        mPosBuf.position(0);

        mUvBuf.position(0);
        mUvBuf.put(uvX1);
        mUvBuf.put(uvY1);
        mUvBuf.put(uvX2);
        mUvBuf.put(uvY2);
        mUvBuf.put(uvX3);
        mUvBuf.put(uvY3);
        mUvBuf.put(uvX4);
        mUvBuf.put(uvY4);
        mUvBuf.position(0);
    }

    @Override
    public void render(Matrix3f matrix) {
        GLES30.glUseProgram(mProgramId);
        GLES30.glUniformMatrix3fv(mUniformMatrixLoc, 1, true, matrix.getValues(), 0);

        GLES30.glVertexAttribPointer(0 , 3,GLES30.GL_FLOAT ,false , 3 * Float.BYTES  , mPosBuf);
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glVertexAttribPointer(1 , 2 , GLES30.GL_FLOAT , false , 2 * Float.BYTES , mUvBuf);
        GLES30.glEnableVertexAttribArray(1);
        GLES30.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES30.glBindTexture(GLES20.GL_TEXTURE_2D , mTextureId);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN , 0 , 4);

        GLES30.glBindTexture(GLES20.GL_TEXTURE_2D , 0);
    }
} //end class
