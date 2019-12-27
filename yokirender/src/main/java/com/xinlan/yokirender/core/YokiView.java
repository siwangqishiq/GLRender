package com.xinlan.yokirender.core;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.xinlan.yokirender.core.math.Vector4f;
import com.xinlan.yokirender.util.OpenglEsUtils;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 *  核心View 类
 *
 */
public abstract  class YokiView extends GLSurfaceView implements GLSurfaceView.Renderer {
    private Context mContext;

    private Vector4f mRefreshColor = new Vector4f();

    private YokiPaint mDefaultPaint;
    private YokiCanvasImpl mRender;

    public YokiView(Context context) {
        super(context);
        init(context);
    }

    public YokiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    protected void init(Context context){
        mContext = context;

        setEGLContextClientVersion(3);
        setEGLConfigChooser(8, 8, 8, 8, 8, 0);

        //requestRender();

        setRenderer(this);
        setRenderMode(RENDERMODE_WHEN_DIRTY);

        mDefaultPaint = new YokiPaint();
        mRender = new YokiCanvasImpl(mDefaultPaint);
    }

    /**
     * 手动请求刷新一次View
     *
     */
    public void refreshView(){
        this.requestRender();
    }

    public abstract void onInit(int width , int height);

    public abstract void onRender(final YokiCanvas canvas);

    /**
     *  设置背景颜色
     *
     * @param refreshColor
     */
    public void setRefreshColor(int refreshColor){
        mRefreshColor = OpenglEsUtils.convertColor(refreshColor);
        //refreshView();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        System.out.println("onSurfaceCreated");

        float[] transM1 = new float[9];



        Camera camera = new Camera(100, 100, 200, 200);

        float m[] = camera.worldToScreen(200,200);
        for(int i = 0 ; i < m.length ; i++){
            System.out.print(m[i] +"   ");
        }
        System.out.println();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        System.out.println("onSurfaceChanged width = " + width +"  height = " + height);
        GLES30.glViewport(0, 0, width, height);

        mRender.onInitSurface(width , height);
        onInit(width , height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        System.out.println("onDrawFrame");
        mRender.clearAllRender();

        GLES30.glClearColor(mRefreshColor.x , mRefreshColor.y, mRefreshColor.z , mRefreshColor.w);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);

        onRender(mRender);

        mRender.render();
    }


}//end class
