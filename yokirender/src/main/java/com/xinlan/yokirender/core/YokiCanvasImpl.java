package com.xinlan.yokirender.core;

import android.content.Context;
import android.graphics.Color;
import android.graphics.RectF;

import com.xinlan.yokirender.core.command.CircleCmd;
import com.xinlan.yokirender.core.command.Cmd;
import com.xinlan.yokirender.core.command.LineCmd;
import com.xinlan.yokirender.core.command.PointCmd;
import com.xinlan.yokirender.core.command.RectCmd;
import com.xinlan.yokirender.core.command.SpriteCmd;
import com.xinlan.yokirender.core.command.TriangleCmd;
import com.xinlan.yokirender.math.Matrix3f;
import com.xinlan.yokirender.math.Vector3f;
import com.xinlan.yokirender.core.pool.CmdPools;
import com.xinlan.yokirender.core.shader.ShaderManager;

import java.util.ArrayList;

public class YokiCanvasImpl implements YokiCanvas {
    private YokiPaint mDefaultPaint;
    private BitManager mBitManager;
    private ArrayList<IRender> mRenderList = new ArrayList<IRender>(256);

    private Camera mCamera;
    private boolean mCull = false; //是否需要视景体剔除

    private float mZorder = 1.0f;
    private static final float ZORDER_DECRESE = 0.0001f;

    private CmdPools mCmdPool = new CmdPools();

    //左边变换矩阵栈
    private static final int MATRIX_STACK_SIZE = 16;
    private Matrix3f mMats[] = new Matrix3f[MATRIX_STACK_SIZE];
    private int mMatUsingIndex = 0;
    private Matrix3f mResult = new Matrix3f();
    private float[] mTransformResults = new float[3];
    private Vector3f mTmpVec = new Vector3f();

    public YokiCanvasImpl(YokiPaint paint, BitManager bm) {
        mDefaultPaint = paint;
        mBitManager = bm;
        if (mDefaultPaint == null) {
            mDefaultPaint = new YokiPaint();
        }
        initMatrixStack();
    }

    private void initMatrixStack() {
        for (int i = 0; i < MATRIX_STACK_SIZE; i++) {
            mMats[i] = new Matrix3f();
            mMats[i].setIdentity();
        }//end for i
        mMatUsingIndex = 0;
        updateResultMatrix();
    }

    public void initEngine(Context context) {
        ShaderManager.getInstance().initShader(context); //着色器代码资源初始化
        primitiveInit();
    }

    public void primitiveInit() {
        mCmdPool.initCmds();
    }

    public void onInitSurface(int w, int h) {
        //mCamera = new Camera(0, 0, w, h); //设置摄像机初始属性  以View大小作为视口
        mCamera = new Camera(0, 0, w, h); //设置摄像机初始属性  以View大小作为视口
    }

    public void clearAllRender() {
        mZorder = 1.0f;
        mRenderList.clear();
    }

    public void render() {
        //1 .视景体的剔除   ??应该在渲染指令层做这个优化吗？？
        if (mCull) {
            //todo cull
        }

        // 2. render
        for (int i = 0, len = mRenderList.size(); i < len; i++) {
            IRender renderObj = mRenderList.get(i);
            renderObj.render(mCamera.getMatrix());
            renderObj.reset();
        }//end for i
    }

    @Override
    public void drawPoint(float x, float y, YokiPaint paint) {
        decreseZorder();
        final PointCmd cmd = mCmdPool.obtainPointCmd();
        transformPoint(x, y);
        cmd.appendRender(mTransformResults[0], mTransformResults[1], mZorder, paint);

        addRenderCmd(cmd);
    }

    @Override
    public void drawLine(float x1, float y1, float x2, float y2, YokiPaint paint) {
        decreseZorder();
        final LineCmd cmd = mCmdPool.obtainLineCmd();

        transformPoint(x1, y1);
        float _x1 = mTransformResults[0];
        float _y1 = mTransformResults[1];

        transformPoint(x2, y2);
        float _x2 = mTransformResults[0];
        float _y2 = mTransformResults[1];

        cmd.update(_x1, _y1, _x2, _y2, mZorder, paint);
        addRenderCmd(cmd);
    }

    @Override
    public void drawTriangle(float x1, float y1, float x2, float y2, float x3, float y3, YokiPaint paint) {
        decreseZorder();
        final TriangleCmd cmd = mCmdPool.obtainTriangleCmd();

        transformPoint(x1, y1);
        float _x1 = mTransformResults[0];
        float _y1 = mTransformResults[1];

        transformPoint(x2, y2);
        float _x2 = mTransformResults[0];
        float _y2 = mTransformResults[1];

        transformPoint(x3, y3);
        float _x3 = mTransformResults[0];
        float _y3 = mTransformResults[1];

        cmd.appendRender(_x1, _y1, _x2, _y2, _x3, _y3, mZorder, paint);
        addRenderCmd(cmd);
    }

    @Override
    public void drawRect(float left, float top, float width, float height, YokiPaint paint) {
        decreseZorder();
        final RectCmd cmd = mCmdPool.obtainRectCmd();

        float x1 = left;
        float y1 = top;
        transformPoint(x1, y1);
        float _x1 = mTransformResults[0];
        float _y1 = mTransformResults[1];

        float x2 = left + width;
        float y2 = top;
        transformPoint(x2, y2);
        float _x2 = mTransformResults[0];
        float _y2 = mTransformResults[1];


        float x3 = left + width;
        float y3 = top - height;
        transformPoint(x3, y3);
        float _x3 = mTransformResults[0];
        float _y3 = mTransformResults[1];

        float x4 = left;
        float y4 = top - height;
        transformPoint(x4, y4);
        float _x4 = mTransformResults[0];
        float _y4 = mTransformResults[1];

        cmd.appendRender(_x1, _y1, _x2, _y2, _x3, _y3, _x4, _y4, mZorder, paint);
        addRenderCmd(cmd);
    }

    @Override
    public void drawCircle(float centerX, float centerY, float radius, YokiPaint paint) {
        decreseZorder();
        final CircleCmd cmd = mCmdPool.obtainCircleCmd();

        float x1 = centerX - radius;
        float y1 = centerY + radius;
        transformPoint(x1, y1);
        float _x1 = mTransformResults[0];
        float _y1 = mTransformResults[1];

        float x2 = centerX + radius;
        float y2 = centerY + radius;
        transformPoint(x2, y2);
        float _x2 = mTransformResults[0];
        float _y2 = mTransformResults[1];

        float x3 = centerX + radius;
        float y3 = centerY - radius;
        transformPoint(x3, y3);
        float _x3 = mTransformResults[0];
        float _y3 = mTransformResults[1];

        float x4 = centerX - radius;
        float y4 = centerY - radius;
        transformPoint(x4, y4);
        float _x4 = mTransformResults[0];
        float _y4 = mTransformResults[1];

        cmd.appendRender(_x1, _y1, _x2, _y2, _x3, _y3, _x4, _y4, mZorder, paint);
        addRenderCmd(cmd);
    }

    @Override
    public void drawSprite(YokiBit bit, RectF srcRect, RectF dstRect, YokiPaint paint) {
        drawSprite(bit, srcRect.left, srcRect.top, srcRect.width(), srcRect.height(),
                dstRect.left, dstRect.top,
                dstRect.width() >= 0 ? dstRect.width() : -dstRect.width(),
                dstRect.height() >= 0 ? dstRect.height() : -dstRect.height());
    }

    @Override
    public void drawSprite(YokiBit bit, float srcLeft, float srcTop, float srcWidth, float srcHeight, float x, float y, float width, float height) {
        decreseZorder();
        SpriteCmd cmd = mCmdPool.obtainSpriteCmd();

        float x1 = x;
        float y1 = y;
        transformPoint(x1, y1);
        float _x1 = mTransformResults[0];
        float _y1 = mTransformResults[1];

        float x2 = x + width;
        float y2 = y;
        transformPoint(x2, y2);
        float _x2 = mTransformResults[0];
        float _y2 = mTransformResults[1];


        float x3 = x + width;
        float y3 = y - height;
        transformPoint(x3, y3);
        float _x3 = mTransformResults[0];
        float _y3 = mTransformResults[1];

        float x4 = x;
        float y4 = y - height;
        transformPoint(x4, y4);
        float _x4 = mTransformResults[0];
        float _y4 = mTransformResults[1];


        float uvX1 = srcLeft / bit.srcWidth;
        float uvY1 = srcTop / bit.srcHeight;

        float uvX2 = (srcLeft + srcWidth) / bit.srcWidth;
        float uvY2 = srcTop / bit.srcHeight;

        float uvX3 = (srcLeft + srcWidth) / bit.srcWidth;
        float uvY3 = (srcTop + srcHeight) / bit.srcHeight;

        float uvX4 = srcLeft / bit.srcWidth;
        float uvY4 = (srcTop + srcHeight) / bit.srcHeight;

        cmd.appendRender(bit.textureId, _x1, _y1, _x2, _y2, _x3, _y3, _x4, _y4, mZorder,
                uvX1, uvY1, uvX2, uvY2, uvX3, uvY3, uvX4, uvY4, null);
        addRenderCmd(cmd);
    }

    @Override
    public void drawText(String content, float x, float y, float textWidth, float textHeight, Color textColor) {
        YokiBit bit = mBitManager.getTextBit(content);
        drawSprite(bit, 0, 0, bit.srcWidth, bit.srcHeight, x, y, textWidth, textHeight);
    }

    @Override
    public void restore() {
        mMatUsingIndex--;
        if (mMatUsingIndex < -1) {
            throw new RuntimeException("can not restore brfore any call save() !");
        }

        updateResultMatrix();
    }

    @Override
    public void save() { //坐标系入栈
        mMatUsingIndex++;
        if (mMatUsingIndex >= mMats.length) {
            throw new RuntimeException("can not restore brfore any call save() !");
        }
        mMats[mMatUsingIndex].setIdentity();
        updateResultMatrix();
    }

    @Override
    public void rotate(float degree) {
        rotate(0, 0, degree);
    }

    @Override
    public void rotate(float centerX, float centerY, float degree) {
        mMats[mMatUsingIndex].postRotate(centerX, centerY, degree);
        updateResultMatrix();
    }

    @Override
    public void scale(float sc) {
        scale(sc, sc);
    }

    @Override
    public void scale(float scaleX, float scaleY) {
        mMats[mMatUsingIndex].postScale(scaleX, scaleY);
        updateResultMatrix();
    }

    @Override
    public void translate(float dx, float dy) {
        mMats[mMatUsingIndex].postTranslate(dx, dy);
        updateResultMatrix();
    }

    /**
     * 根据当前矩阵栈变换顶点坐标
     *
     * @param _x
     * @param _y
     */
    private void transformPoint(float _x, float _y) {
        mTmpVec.x = _x;
        mTmpVec.y = _y;
        mTmpVec.z = 1.0f;

        mTmpVec.multiMatrix(mResult);

        mTransformResults[0] = mTmpVec.x;
        mTransformResults[1] = mTmpVec.y;
    }

    /**
     * 更新坐标系变换矩阵
     */
    private void updateResultMatrix() {
        mResult.setIdentity();

        if (mMatUsingIndex >= 0) {
            for (int i = 0; i <= mMatUsingIndex; i++) {
                mResult.mul(mMats[i]);
            }//end for i
        }
    }

    @Override
    public Camera getCamera() {
        return mCamera;
    }

    /**
     * 将渲染指令添加到渲染列表中去
     *
     * @param cmd
     */
    public void addRenderCmd(final Cmd cmd) {
        if (cmd == null || cmd.addRenderList)
            return;

        //System.out.println("add to render list  index = " + cmd.mIndex);
        cmd.addRenderList = true;
        mRenderList.add(cmd);
    }

    public void decreseZorder() {
        mZorder -= ZORDER_DECRESE;
    }
}//end class
