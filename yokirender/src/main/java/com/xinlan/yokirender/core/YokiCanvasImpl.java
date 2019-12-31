package com.xinlan.yokirender.core;

import com.xinlan.yokirender.core.command.PointCmd;
import com.xinlan.yokirender.core.primitive.IRender;
import com.xinlan.yokirender.core.primitive.Point;

import java.util.ArrayList;

public class YokiCanvasImpl implements YokiCanvas {
    private YokiPaint mDefaultPaint;
    private ArrayList<IRender> mRenderList = new ArrayList<IRender>(64);

    private Camera mCamera;
    private boolean mCull = false; //是否需要视景体剔除

    private CmdPool mCmdPool;


    public YokiCanvasImpl(YokiPaint paint) {
        mDefaultPaint = paint;
        mCmdPool = new CmdPool();
        if (mDefaultPaint == null) {
            mDefaultPaint = new YokiPaint();
        }
    }

    public void initEngine() {
        primitiveInit();
    }

    public void primitiveInit() {
        mCmdPool.initCmds();
    }

    public void onInitSurface(int w, int h) {
        mCamera = new Camera(0, 0, w, h);
    }

    public void clearAllRender() {
        mRenderList.clear();
    }

    public void render() {
        //1 .视景体的剔除
        if (mCull) {

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
        PointCmd pointCmd = mCmdPool.obtainPointCmd();
        pointCmd.reset(x , y , paint);
        mRenderList.add(pointCmd);
    }

    @Override
    public void drawLine(float x1, float y1, float x2, float y2, YokiPaint paint) {

    }

    @Override
    public void drawTriangle(float x1, float y1, float x2, float y2, float x3, float y3, YokiPaint paint) {

    }

    @Override
    public void drawRect(float left, float top, float width, float height, YokiPaint paint) {

    }

    @Override
    public void save() {

    }

    @Override
    public void rotate(float degree, float centerX, float centerY) {

    }

    @Override
    public void scale(float scaleX, float scaleY) {

    }

    @Override
    public void transform(float deltaX, float deltaY) {

    }

    @Override
    public void restore() {

    }

    @Override
    public Camera getCamera() {
        return mCamera;
    }
}//end class
