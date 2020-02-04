package com.xinlan.yokirender.core.command;

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
    private FloatBuffer mTextureBuf;

    @Override
    public void init() {
        mProgramId = ShaderManager.getInstance().findProgramId(RENDER_SPRITE);
        mUniformMatrixLoc = GLES30.glGetUniformLocation(mProgramId , "uMatrix");

        mPosBuf = allocateFloatBufBySize(4 * 3);
    }

    public void appendRender(
            float _x1, float _y1,
            float _x2, float _y2,
            float _x3, float _y3,
            float _x4, float _y4,
            float zOrder, YokiPaint paint){

    }

    @Override
    public void render(Matrix3f matrix) {
        GLES30.glUseProgram(mProgramId);
    }
} //end class
