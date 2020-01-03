package com.xinlan.yokirender.core.command;

import android.opengl.GLES30;

import com.xinlan.yokirender.core.YokiPaint;
import com.xinlan.yokirender.core.math.Matrix3f;
import com.xinlan.yokirender.core.primitive.ShaderManager;

/**
 *   线段绘制指令
 *
 */
public class LineCmd extends Cmd {
    public static final String RENDER_LINE = "_render_line";

    public static LineCmd newInstance() {
        LineCmd cmd = new LineCmd();
        cmd.init();
        return cmd;
    }

    @Override
    public void init() {
        mProgramId = ShaderManager.getInstance().findProgramId(RENDER_LINE);
        mUniformMatrixLoc = GLES30.glGetUniformLocation(mProgramId , "uMatrix");

        int buffIds[] = new int[4];
        GLES30.glGenBuffers(4 , buffIds , 0);

    }

    public void reset(float _x1 , float _y1 , float _x2 , float _y2 , YokiPaint paint){

    }

    @Override
    public void render(Matrix3f matrix) {

    }

}//end class
