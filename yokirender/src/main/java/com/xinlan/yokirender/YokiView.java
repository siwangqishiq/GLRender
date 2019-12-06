package com.xinlan.yokirender;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 *  核心类
 *
 */
public class YokiView extends GLSurfaceView implements GLSurfaceView.Renderer {
    private Context mContext;

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
        setRenderer(this);
        setRenderMode(RENDERMODE_WHEN_DIRTY);
        //requestRender();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        System.out.println("onSurfaceCreated");
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        System.out.println("onSurfaceChanged width = " + width +"  height = " + height);
        GLES30.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        System.out.println("onDrawFrame");
        GLES30.glClearColor(1.0f , 1.0f , 1.0f , 1.0f);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);
    }
}//end class
