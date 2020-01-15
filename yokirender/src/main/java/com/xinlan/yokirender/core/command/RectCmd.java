package com.xinlan.yokirender.core.command;

import android.opengl.GLES30;

import com.xinlan.yokirender.core.YokiPaint;
import com.xinlan.yokirender.core.shader.ShaderManager;
import com.xinlan.yokirender.math.Matrix3f;
import com.xinlan.yokirender.util.OpenglEsUtils;

import java.nio.IntBuffer;

/**
 *   矩形绘制指令
 */
public class RectCmd extends Cmd {
    public final int ELEMENT_COUNT = 1024; //渲染指令可渲染元素数量
    public final int vertexPerElement = 4;

    public static RectCmd newInstance() {
        RectCmd cmd = new RectCmd();
        cmd.init();
        return cmd;
    }

    protected int mIndicesBufId;
    protected IntBuffer mIndicesBuf;

    protected float mColor[] = new float[4];

    @Override
    public void init() {
        mProgramId = ShaderManager.getInstance().findProgramId(TriangleCmd.RENDER_TRIANGLE);
        mUniformMatrixLoc = GLES30.glGetUniformLocation(mProgramId , "uMatrix");

        //显存空间开辟2个缓冲区  一个存位置 一个存颜色
        int buffIds[] = new int[3];
        GLES30.glGenBuffers(2 , buffIds , 0);
        mPosBufId = buffIds[0];
        mPosBuf = allocateFloatBufBySize(ELEMENT_COUNT * vertexPerElement * 3);

        mColorBufId = buffIds[1];
        mColorBuf = allocateFloatBufBySize(ELEMENT_COUNT * vertexPerElement * 4);

        mIndicesBufId = buffIds[2];
        mIndicesBuf = OpenglEsUtils.allocateIntBuf(ELEMENT_COUNT* (vertexPerElement + 1) );
    }

    public void update(float _x1 , float _y1 ,
                       float _x2 , float _y2 ,
                       float _x3 , float _y3 ,
                       float _x4 , float _y4 ,
                       float zOrder, YokiPaint paint){
        mColor[0] = paint.color.x;
        mColor[1] = paint.color.y;
        mColor[2] = paint.color.z;
        mColor[3] = paint.color.w;


//        mPosBuf.position(0);
//        mPosBuf.put(left);
//        mPosBuf.put(top);
//        mPosBuf.put(zOrder);
//        mPosBuf.put(left + width);
//        mPosBuf.put(top);
//        mPosBuf.put(zOrder);
//        mPosBuf.put(left + width);
//        mPosBuf.put(top - height);
//        mPosBuf.put(zOrder);
//        mPosBuf.put(left);
//        mPosBuf.put(top - height);
//        mPosBuf.put(zOrder);
//        mPosBuf.position(0);
//
//        mColorBuf.position(0);
//        for(int i = 0 , count = vertexCount() ; i <count ;i++){
//            mColorBuf.put(paint.color.x);
//            mColorBuf.put(paint.color.y);
//            mColorBuf.put(paint.color.z);
//            mColorBuf.put(paint.color.w);
//        }//end for i
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

        mIndicesBuf.position(0);
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER , mIndicesBufId);
        GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER ,
                vertextCount * vertexPerElement * 4 * Float.BYTES , mIndicesBuf , GLES30.GL_DYNAMIC_DRAW );

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER , 0);
    }


    @Override
    public void render(Matrix3f matrix) {
        updateBuffer();

        GLES30.glEnable(GLES30.GL_PRIMITIVE_RESTART_FIXED_INDEX);

        GLES30.glUseProgram(mProgramId);
        GLES30.glUniformMatrix3fv(mUniformMatrixLoc ,1 , true , matrix.getValues() , 0);

        GLES30.glEnableVertexAttribArray(0);
        GLES30.glEnableVertexAttribArray(1);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER  , mPosBufId);
        GLES30.glVertexAttribPointer(0 , 3 , GLES30.GL_FLOAT , false , 3 * Float.BYTES , 0);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER  , mColorBufId);
        GLES30.glVertexAttribPointer(1 , 4 , GLES30.GL_FLOAT , false ,  4 * Float.BYTES , 0);

        GLES30.glDrawElements(GLES30.GL_TRIANGLE_FAN , mIndex ,GLES30.GL_INT , mIndicesBuf );

        GLES30.glDisableVertexAttribArray(0);
        GLES30.glDisableVertexAttribArray(1);
    }
}//end class
