package com.xinlan.yokirender.core.view;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

import com.xinlan.yokirender.core.GLConfig;
import com.xinlan.yokirender.core.YokiCanvas;
import com.xinlan.yokirender.core.YokiCanvasImpl;
import com.xinlan.yokirender.core.YokiPaint;
import com.xinlan.yokirender.core.math.Vector4f;
import com.xinlan.yokirender.util.OpenglEsUtils;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 *  核心View 类
 *
 */
public abstract  class YokiView extends GLSurfaceView implements GLSurfaceView.Renderer {
    private static final String TAG = YokiView.class.getSimpleName();

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

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG , "onDetachedFromWindow ");
       //ShaderManager.ctx = null;
    }

    protected void init(Context context){
        mContext = context;
        //ShaderManager.ctx = context;
        setEGLContextClientVersion(3);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);

        //requestRender();

        setRenderer(this);
//        setRenderMode(RENDERMODE_WHEN_DIRTY);
        setRenderMode(RENDERMODE_CONTINUOUSLY);

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
        Log.d(TAG , "onSurfaceCreated");

        readGLConfig();
        mRender.initEngine(mContext);
    }

    /**
     *
     */
    private void readGLConfig(){
        final ActivityManager activityManager = (ActivityManager)mContext.getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo =activityManager.getDeviceConfigurationInfo();
        int version = configurationInfo.reqGlEsVersion;
        String versionName = configurationInfo.getGlEsVersion();

        GLConfig.glVersion = version;
        GLConfig.glVersionName = versionName;

        Log.d(TAG, "GL version  "+Integer.toHexString(version)+" , "+ versionName);

        float pointSizeBuffers[] = new float[2];
        GLES30.glGetFloatv(GLES30.GL_ALIASED_POINT_SIZE_RANGE , pointSizeBuffers , 0);
        GLConfig.maxPointSize = pointSizeBuffers[1];

        float lineWidthRangeBuffers[] = new float[2];
        GLES30.glGetFloatv(GLES30.GL_ALIASED_LINE_WIDTH_RANGE , lineWidthRangeBuffers , 0);
        GLConfig.maxLineWidth = lineWidthRangeBuffers[1];

        Log.d(TAG, "GL pointSizeRange  = "+pointSizeBuffers[0] +" - "+ pointSizeBuffers[1]);
        Log.d(TAG, "GL lineWidthRange  = "+lineWidthRangeBuffers[0] +" - "+ lineWidthRangeBuffers[1]);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.d(TAG , "onSurfaceChanged width = " + width +"  height = " + height);
        GLES30.glViewport(0, 0, width, height);
        GLES30.glEnable(GLES30.GL_DEPTH_TEST); //打开深度测试

        mRender.onInitSurface(width , height);
        onInit(width , height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //System.out.println("onDrawFrame");
        mRender.clearAllRender();

        long t1 = System.currentTimeMillis();

        GLES30.glClearColor(mRefreshColor.x , mRefreshColor.y, mRefreshColor.z , mRefreshColor.w);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);

        final long t5 = System.currentTimeMillis();
        onRender(mRender);
        final long t6 = System.currentTimeMillis();
        Log.d(TAG, "view draw content time = " + (t6  - t5));

        final long t3 = System.currentTimeMillis();
        mRender.render();
        final long t4 = System.currentTimeMillis();
        Log.d(TAG, "gl call time = " + (t4  - t3));

        long t2 = System.currentTimeMillis();
        Log.d(TAG, "render a frame time = " + (t2  - t1));

    }


}//end class
