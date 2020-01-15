package com.xinlan.yokirender.core;

import android.content.Context;

import com.xinlan.yokirender.core.command.Cmd;
import com.xinlan.yokirender.core.command.LineCmd;
import com.xinlan.yokirender.core.command.PointCmd;
import com.xinlan.yokirender.core.command.RectCmd;
import com.xinlan.yokirender.core.command.TriangleCmd;
import com.xinlan.yokirender.math.Matrix3f;
import com.xinlan.yokirender.math.Vector3f;
import com.xinlan.yokirender.core.pool.CmdPools;
import com.xinlan.yokirender.core.shader.ShaderManager;

import java.util.ArrayList;

public class YokiCanvasImpl implements YokiCanvas {
    private YokiPaint mDefaultPaint;
    private ArrayList<IRender> mRenderList = new ArrayList<IRender>(256);

    private Camera mCamera;
    private boolean mCull = false; //是否需要视景体剔除

    private float mZorder = 1.0f;
    private static final float ZORDER_DECRESE = 0.0001f;

    private CmdPools mCmdPool = new CmdPools();

    //左边变换矩阵栈
    private static final int MATRIX_STACK_SIZE =16;
    private Matrix3f mMats[] = new Matrix3f[MATRIX_STACK_SIZE];
    private int mMatUsingIndex = 0;
    private Matrix3f mResult = new Matrix3f();
    private float[] mTransformResults = new float[3];
    private Vector3f mTmpVec = new Vector3f();

    public YokiCanvasImpl(YokiPaint paint) {
        mDefaultPaint = paint;
        if (mDefaultPaint == null) {
            mDefaultPaint = new YokiPaint();
        }
        initMatrixStack();
    }

    private void initMatrixStack() {
        for(int i = 0 ; i < MATRIX_STACK_SIZE ; i++){
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
        transformPoint(x , y);
        cmd.appendRender(mTransformResults[0] , mTransformResults[1] , mZorder , paint);

        addRenderCmd(cmd);
    }

    @Override
    public void drawLine(float x1, float y1, float x2, float y2, YokiPaint paint) {
        decreseZorder();
        final LineCmd cmd = mCmdPool.obtainLineCmd();

        transformPoint(x1 , y1);
        float _x1 = mTransformResults[0];
        float _y1 = mTransformResults[1];

        transformPoint(x2 , y2);
        float _x2 = mTransformResults[0];
        float _y2 = mTransformResults[1];

        cmd.update(_x1 , _y1 , _x2 , _y2 , mZorder , paint);
        addRenderCmd(cmd);
    }

    @Override
    public void drawTriangle(float x1, float y1, float x2, float y2, float x3, float y3, YokiPaint paint) {
        decreseZorder();
        final TriangleCmd cmd = mCmdPool.obtainTriangleCmd();

        transformPoint(x1 , y1);
        float _x1 = mTransformResults[0];
        float _y1 = mTransformResults[1];

        transformPoint(x2 , y2);
        float _x2 = mTransformResults[0];
        float _y2 = mTransformResults[1];

        transformPoint(x3 , y3);
        float _x3 = mTransformResults[0];
        float _y3 = mTransformResults[1];

        cmd.appendRender(_x1 , _y1 , _x2 , _y2 , _x3 , _y3 , mZorder ,paint);

        addRenderCmd(cmd);
    }

    @Override
    public void drawRect(float left, float top, float width, float height, YokiPaint paint) {
        decreseZorder();
        final RectCmd cmd = mCmdPool.obtainRectCmd();
//        cmd.update(left , top , width , height , mZorder,paint);
//
//        addRenderCmd(cmd);

    }

    @Override
    public void restore() {
        mMatUsingIndex--;
        if(mMatUsingIndex < -1){
            throw new RuntimeException("can not restore brfore any call save() !");
        }

        updateResultMatrix();
    }

    @Override
    public void save() { //坐标系入栈
        mMatUsingIndex++;
        if(mMatUsingIndex >= mMats.length) {
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
        mMats[mMatUsingIndex].postRotate(centerX , centerY ,degree);
        updateResultMatrix();
    }

    @Override
    public void scale(float sc) {
        scale(sc , sc);
    }

    @Override
    public void scale(float scaleX, float scaleY) {
        mMats[mMatUsingIndex].postScale(scaleX , scaleY);
        updateResultMatrix();
    }

    @Override
    public void translate(float dx, float dy) {
        mMats[mMatUsingIndex].postTranslate(dx , dy);
        updateResultMatrix();
    }

    /**
     *  根据当前矩阵栈变换顶点坐标
     *
     * @param _x
     * @param _y
     */
    private void transformPoint(float _x , float _y) {
        mTmpVec.x = _x;
        mTmpVec.y = _y;
        mTmpVec.z = 1.0f;

        mTmpVec.multiMatrix(mResult);

        mTransformResults[0] = mTmpVec.x;
        mTransformResults[1] = mTmpVec.y;
    }

    /**
     *  更新坐标系变换矩阵
     */
    private void updateResultMatrix() {
        mResult.setIdentity();

        if(mMatUsingIndex >= 0){
            for(int i = 0 ; i <= mMatUsingIndex;i++){
                mResult.mul(mMats[i]);
            }//end for i
        }
    }

    @Override
    public Camera getCamera() {
        return mCamera;
    }

    /**
     *   将渲染指令添加到渲染列表中去
     * @param cmd
     */
    public void addRenderCmd(final Cmd cmd){
        if(cmd == null || cmd.addRenderList)
            return;

        //System.out.println("add to render list  index = " + cmd.mIndex);
        cmd.addRenderList = true;
        mRenderList.add(cmd);
    }

    public void decreseZorder() {
        mZorder -= ZORDER_DECRESE;
    }
}//end class
