package com.xinlan.yokirender.core.command;

import android.opengl.GLES30;

import com.xinlan.yokirender.core.GLConfig;
import com.xinlan.yokirender.core.YokiPaint;
import com.xinlan.yokirender.core.math.Matrix3f;
import com.xinlan.yokirender.core.shader.ShaderManager;

import java.nio.FloatBuffer;

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

    private int mPosBufId;
    private int mColorBufId;

    private FloatBuffer mPosBuf; //直线坐标数据
    private FloatBuffer mColorBuf; //直线颜色

    private float mLineWidth;

    @Override
    public void init() {
        mProgramId = ShaderManager.getInstance().findProgramId(RENDER_LINE);
        mUniformMatrixLoc = GLES30.glGetUniformLocation(mProgramId , "uMatrix");

        //显存空间开辟2个缓冲区  一个存位置 一个存颜色
        int buffIds[] = new int[2];
        GLES30.glGenBuffers(2 , buffIds , 0);

        mPosBufId = buffIds[0];
        mPosBuf = allocateFloatBufBySize(2 * 3);
        //mPosBuf = OpenglEsUtils.allocateBuf(new float[2 * 2]);

        mColorBufId = buffIds[1];
        mColorBuf = allocateFloatBufBySize(2 * 4);
        //mColorBuf = OpenglEsUtils.allocateBuf(new float[2 * 4]);

        updateBuffer();
    }

    private void updateBuffer() {
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER , mPosBufId);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER , 2 * 3 * Float.BYTES , mPosBuf , GLES30.GL_DYNAMIC_DRAW);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER , mColorBufId);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER ,  2 * 4 * Float.BYTES , mColorBuf , GLES30.GL_DYNAMIC_DRAW);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER , 0);
    }

    public void update(float _x1 , float _y1 , float _x2 , float _y2 , float zOrder ,YokiPaint paint){
        mPosBuf.position(0);
        mPosBuf.put(_x1);
        mPosBuf.put(_y1);
        mPosBuf.put(zOrder);

        mPosBuf.put(_x2);
        mPosBuf.put(_y2);
        mPosBuf.put(zOrder);
        mPosBuf.position(0);

        mColorBuf.position(0);
        mColorBuf.put(paint.color.x);
        mColorBuf.put(paint.color.y);
        mColorBuf.put(paint.color.z);
        mColorBuf.put(paint.color.w);

        mColorBuf.put(paint.color.x);
        mColorBuf.put(paint.color.y);
        mColorBuf.put(paint.color.z);
        mColorBuf.put(paint.color.w);
        mColorBuf.position(0);

        if(paint.size <= GLConfig.maxLineWidth) {
            mLineWidth = paint.size;
        }

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

        GLES30.glLineWidth(mLineWidth); //设置线段宽度

        GLES30.glDrawArrays(GLES30.GL_LINES , 0 , 2);

        GLES30.glDisableVertexAttribArray(0);
        GLES30.glDisableVertexAttribArray(1);
    }
}//end class
