package com.xinlan.yokirender.core;

import android.content.Context;

import com.xinlan.yokirender.core.command.Cmd;
import com.xinlan.yokirender.core.command.LineCmd;
import com.xinlan.yokirender.core.command.PointCmd;
import com.xinlan.yokirender.core.command.RectCmd;
import com.xinlan.yokirender.core.command.TriangleCmd;
import com.xinlan.yokirender.core.pool.CmdPools;
import com.xinlan.yokirender.core.shader.ShaderManager;

import java.util.ArrayList;

public class YokiCanvasImpl implements YokiCanvas {
    private YokiPaint mDefaultPaint;
    private ArrayList<IRender> mRenderList = new ArrayList<IRender>(64);

    private Camera mCamera;
    private boolean mCull = false; //是否需要视景体剔除

    private float mZorder = 1.0f;
    private static final float ZORDER_DECRESE = 0.0001f;

    private CmdPools mCmdPool = new CmdPools();


    public YokiCanvasImpl(YokiPaint paint) {
        mDefaultPaint = paint;
        if (mDefaultPaint == null) {
            mDefaultPaint = new YokiPaint();
        }
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
        cmd.update(x , y , mZorder ,paint);

        addRenderCmd(cmd);
    }

    @Override
    public void drawLine(float x1, float y1, float x2, float y2, YokiPaint paint) {
        decreseZorder();
        final LineCmd cmd = mCmdPool.obtainLineCmd();
        cmd.update(x1 ,  y1 , x2 , y2 , mZorder , paint);

        addRenderCmd(cmd);
    }

    @Override
    public void drawTriangle(float x1, float y1, float x2, float y2, float x3, float y3, YokiPaint paint) {
        decreseZorder();
        final TriangleCmd cmd = mCmdPool.obtainTriangleCmd();
        cmd.update(x1 , y1 , x2 , y2 , x3 , y3 , mZorder ,paint);

        addRenderCmd(cmd);
    }

    @Override
    public void drawRect(float left, float top, float width, float height, YokiPaint paint) {
        decreseZorder();
        final RectCmd cmd = mCmdPool.obtainRectCmd();
        cmd.update(left , top , width , height , mZorder,paint);

        addRenderCmd(cmd);
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

    /**
     *   将渲染指令添加到渲染列表中去
     * @param cmd
     */
    public void addRenderCmd(final Cmd cmd){
        if(cmd == null)
            return;

        mRenderList.add(cmd);
    }

    public void decreseZorder() {
        mZorder -= ZORDER_DECRESE;
    }
}//end class
