package com.xinlan.yokirender.core.command;

import android.opengl.GLES30;

import com.xinlan.yokirender.R;
import com.xinlan.yokirender.core.YokiPaint;
import com.xinlan.yokirender.core.math.Matrix3f;
import com.xinlan.yokirender.core.primitive.Shader;
import com.xinlan.yokirender.util.OpenglEsUtils;

/**
 * 点 render 指令
 */
public class PointCmd extends Cmd {
    public static final String RENDER_POINT = "_render_point";

    private float data[] = new float[2];
    private float color[] = new float[4];
    private float size = 1.0f;

    public static String vertexShaderSrc() {
        return
                        "#version 320 es\n" +
                        "layout(location = 0) in vec4 aColor;\n" +
                        "layout(location = 1) in vec2 aPos;\n" +
                        "layout(location = 2) in float pointSize;\n" +
                        "uniform mat3 uMatrix;\n" +
                        "out vec4 vColor;\n" +
                        "void main(){\n" +
                        "    gl_Position = uMatrix * vec3(aPos.xy , 1.0f);\n" +
                        "    gl_PointSize = pointSize;\n" +
                        "    vColor = aColor;\n" +
                        "}";
    }

    public static String fragShaderSrc(){
        return
                        "#version 320 es\n" +
                        "precision mediump float;\n" +
                        "in vec4 vColor;\n" +
                        "out vec4 fragColor;\n" +
                        "void main(){\n" +
                        "    fragColor = vColor;\n" +
                        "}";
    }

    public void init(){
        programId = Shader.getInstance().findProgramId(RENDER_POINT);

        //GLES30.glGenBuffers(1 , );
    }

    @Override
    public void render(Matrix3f matrix) {
        /**
         *   GLES30.glUseProgram(mProgramId);
         *         GLES30.glUniformMatrix4fv(mUMvpMatrixLoc,
         *                 1 , false , MatrixState.getInstance().getFinalMatrix() , 0);
         *         GLES30.glVertexAttribPointer(0 ,4 ,  GLES20.GL_FLOAT , false , 0 , mColorBuf);
         *         GLES30.glEnableVertexAttribArray(0);
         *         GLES30.glVertexAttribPointer(1 ,3 ,  GLES20.GL_FLOAT ,false ,  0 , mPositionBuf);
         *         GLES30.glEnableVertexAttribArray(1);
         *         GLES30.glVertexAttrib1f(2,mPointSize);
         *
         *         GLES30.glDrawArrays(GLES30.GL_POINTS , 0 , 1);
         */
    }

    public void reset(float x , float y , YokiPaint paint) {

    }
} //end class
