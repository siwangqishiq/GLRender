package com.xinlan.yokirender.core.command;

import android.opengl.GLES30;

import com.xinlan.yokirender.core.YokiPaint;
import com.xinlan.yokirender.core.shader.ShaderManager;
import com.xinlan.yokirender.math.Matrix3f;
import com.xinlan.yokirender.util.OpenglEsUtils;

import java.nio.FloatBuffer;

/**
 * 绘制实心圆 指令
 */
public class CircleCmd extends RectCmd {
    public static final String RENDER_SOLID_CIRCLE = "_render_solid_circle";

    public static CircleCmd newInstance() {
        CircleCmd cmd = new CircleCmd();
        cmd.init();
        return cmd;
    }

    protected int mCenterBufId;
    protected FloatBuffer mCenterBuf;

    protected int mRadiusBufId;
    protected FloatBuffer mRadiusBuf;

    @Override
    public void init() {
        mProgramId = ShaderManager.getInstance().findProgramId(RENDER_SOLID_CIRCLE);
        mUniformMatrixLoc = GLES30.glGetUniformLocation(mProgramId, "uMatrix");

        //显存空间开辟5个缓冲区  位置  颜色 顶点索引  圆心坐标 半径长度
        int buffIds[] = new int[5];
        GLES30.glGenBuffers(3, buffIds, 0);
        mPosBufId = buffIds[0];
        mPosBuf = allocateFloatBufBySize(ELEMENT_COUNT * vertexPerElement * 3);

        mColorBufId = buffIds[1];
        mColorBuf = allocateFloatBufBySize(ELEMENT_COUNT * vertexPerElement * 4);

        mIndicesBufId = buffIds[2];
        mIndicesBuf = OpenglEsUtils.allocateIntBuf(ELEMENT_COUNT * (vertexPerElement + 1));

        mCenterBufId = buffIds[3];
        mCenterBuf = allocateFloatBufBySize(ELEMENT_COUNT * vertexPerElement * 2);

        mRadiusBufId = buffIds[4];
        mRadiusBuf = allocateFloatBufBySize(ELEMENT_COUNT * vertexPerElement);
    }

    public void appendRender(
            float _x1, float _y1,
            float _x2, float _y2,
            float _x3, float _y3,
            float _x4, float _y4,
            float zOrder, YokiPaint paint) {

        final float centerX = (_x1 + _x4) / 2;
        final float centerY = (_y1 + _y4) / 2;
        final float radius = Math.abs(_x2 - _x1) / 2;


        mPosBuf.put(_x1);
        mPosBuf.put(_y1);
        mPosBuf.put(zOrder);
        mIndicesBuf.put(mVertexIndex);
        mVertexIndex++;
        mVertexIndexCount++;

        mPosBuf.put(_x2);
        mPosBuf.put(_y2);
        mPosBuf.put(zOrder);
        mIndicesBuf.put(mVertexIndex);
        mVertexIndex++;
        mVertexIndexCount++;

        mPosBuf.put(_x3);
        mPosBuf.put(_y3);
        mPosBuf.put(zOrder);
        mIndicesBuf.put(mVertexIndex);
        mVertexIndex++;
        mVertexIndexCount++;

        mPosBuf.put(_x4);
        mPosBuf.put(_y4);
        mPosBuf.put(zOrder);
        mIndicesBuf.put(mVertexIndex);
        mVertexIndex++;
        mVertexIndexCount++;

        //插入标识索引失效的标志位
        mIndicesBuf.put(0xFFFFFFFF);
        mVertexIndexCount++;

        for (int i = 0; i < 4; i++) {
            mColorBuf.put(paint.color.x);
            mColorBuf.put(paint.color.y);
            mColorBuf.put(paint.color.z);
            mColorBuf.put(paint.color.w);

            mCenterBuf.put(centerX);
            mCenterBuf.put(centerY);

            mRadiusBuf.put(radius);
        }//end for i

        increaseIndex(ELEMENT_COUNT);
    }

    @Override
    protected void updateBuffer() {
        final int vertextCount = mIndex;

        mPosBuf.position(0);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, mPosBufId);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER,
                vertextCount * vertexPerElement * 3 * Float.BYTES,
                mPosBuf, GLES30.GL_DYNAMIC_DRAW);

        mColorBuf.position(0);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, mColorBufId);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER,
                vertextCount * vertexPerElement * 4 * Float.BYTES,
                mColorBuf, GLES30.GL_DYNAMIC_DRAW);

        mCenterBuf.position(0);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, mCenterBufId);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER,
                vertextCount * vertexPerElement * 2 * Float.BYTES,
                mCenterBuf, GLES30.GL_DYNAMIC_DRAW);

        mRadiusBuf.position(0);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, mRadiusBufId);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER,
                vertextCount * vertexPerElement * Float.BYTES,
                mRadiusBuf, GLES30.GL_DYNAMIC_DRAW);

        mIndicesBuf.position(0);
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, mIndicesBufId);
        GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER,
                mVertexIndexCount * Integer.BYTES, mIndicesBuf, GLES30.GL_DYNAMIC_DRAW);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    @Override
    public void render(Matrix3f matrix) {
        updateBuffer();

        GLES30.glEnable(GLES30.GL_PRIMITIVE_RESTART_FIXED_INDEX);

        GLES30.glUseProgram(mProgramId);
        GLES30.glUniformMatrix3fv(mUniformMatrixLoc, 1, true, matrix.getValues(), 0);

        GLES30.glEnableVertexAttribArray(0);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, mPosBufId);
        GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 3 * Float.BYTES, 0);

        GLES30.glEnableVertexAttribArray(1);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, mColorBufId);
        GLES30.glVertexAttribPointer(1, 4, GLES30.GL_FLOAT, false, 4 * Float.BYTES, 0);

        GLES30.glEnableVertexAttribArray(2);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, mRadiusBufId);
        GLES30.glVertexAttribPointer(2, 1, GLES30.GL_FLOAT, false,  Float.BYTES, 0);

        GLES30.glEnableVertexAttribArray(3);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, mCenterBufId);
        GLES30.glVertexAttribPointer(3, 2, GLES30.GL_FLOAT, false, 2 * Float.BYTES, 0);

        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, mIndicesBufId);
        GLES30.glDrawElements(GLES30.GL_TRIANGLE_FAN, mVertexIndexCount, GLES30.GL_UNSIGNED_INT, 0);


        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, 0);

        GLES30.glDisableVertexAttribArray(0);
        GLES30.glDisableVertexAttribArray(1);
        GLES30.glDisableVertexAttribArray(2);
        GLES30.glDisableVertexAttribArray(3);

        GLES30.glDisable(GLES30.GL_PRIMITIVE_RESTART_FIXED_INDEX);
    }

    @Override
    public void reset() {
        super.reset();
        mPosBuf.position(0);
        mCenterBuf.position(0);
        mRadiusBuf.position(0);
        mColorBuf.position(0);
        mIndicesBuf.position(0);
    }

}//end class
