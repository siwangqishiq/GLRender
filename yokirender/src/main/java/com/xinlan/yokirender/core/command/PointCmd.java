package com.xinlan.yokirender.core.command;

import android.opengl.GLES30;

import com.xinlan.yokirender.R;
import com.xinlan.yokirender.core.YokiPaint;
import com.xinlan.yokirender.core.math.Matrix3f;
import com.xinlan.yokirender.core.primitive.ShaderManager;
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

    public static String vertexShaderSrc() {
        return ShaderManager.getSrc(R.raw.render_point_vertex);
    }

    public static String fragShaderSrc(){
        return ShaderManager.getSrc(R.raw.render_point_fragment);
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

    public void reset(float x , float y , YokiPaint paint) {
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


    /**
     * float mColors[] = new float[4];
     *         FloatBuffer mColorBuf = OpenglEsUtils.allocateBuf(mColors);
     *         mColorBuf.position(0);
     *         mColorBuf.put(1.0f);
     *         mColorBuf.put(0.0f);
     *         mColorBuf.put(0.0f);
     *         mColorBuf.put(1.0f);
     *         mColorBuf.position(0);
     *
     *
     *         float mPosition[] = new float[3];
     *         FloatBuffer mPositionBuf = OpenglEsUtils.allocateBuf(mPosition);
     *         mPositionBuf.put(0.0f);
     *         mPositionBuf.put(0.0f);
     *         mPositionBuf.put(0.0f);
     *         mPositionBuf.position(0);
     *
     *         String vSrc =
     *                 "#version 300 es\n" +
     *                 "\n" +
     *                 "layout(location = 0) in vec4 aColor;\n" +
     *                 "layout(location = 1) in vec3 aPos;\n" +
     *                 "\n" +
     *                 "out vec4 vColor;\n" +
     *                 "\n" +
     *                 "void main(){\n" +
     *                 "    gl_Position = vec4(aPos.xyz , 1.0f);\n" +
     *                 "    gl_PointSize = 10.0f;\n" +
     *                 "    vColor = aColor;\n" +
     *                 "}";
     *         String fSrc =
     *                 "#version 300 es\n" +
     *                 "\n" +
     *                 "precision mediump float;\n" +
     *                 "\n" +
     *                 "in vec4 vColor;\n" +
     *                 "out vec4 fragColor;\n" +
     *                 "\n" +
     *                 "void main(){\n" +
     *                 "    fragColor = vColor;\n" +
     *                 "}";
     *         int program = ShaderUtil.buildShaderProgram(vSrc , fSrc);
     *         GLES30.glUseProgram(program);
     *
     * //        GLES30.glEnable(GL_POINT);
     *
     *         GLES30.glVertexAttribPointer(0 ,4 ,  GLES30.GL_FLOAT , false , 4 * 4 , mColorBuf);
     *         GLES30.glEnableVertexAttribArray(0);
     *         GLES30.glVertexAttribPointer(1 ,3 ,  GLES30.GL_FLOAT ,false ,  3 * 4 , mPositionBuf);
     *         GLES30.glEnableVertexAttribArray(1);
     *
     *         GLES30.glDrawArrays(GLES30.GL_POINTS , 0 , 1);
     */

} //end class
