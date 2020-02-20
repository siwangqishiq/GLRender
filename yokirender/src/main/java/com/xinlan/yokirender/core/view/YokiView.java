package com.xinlan.yokirender.core.view;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

import com.xinlan.yokirender.R;
import com.xinlan.yokirender.core.BitManager;
import com.xinlan.yokirender.core.GLConfig;
import com.xinlan.yokirender.core.YokiBit;
import com.xinlan.yokirender.core.YokiCanvas;
import com.xinlan.yokirender.core.YokiCanvasImpl;
import com.xinlan.yokirender.core.YokiPaint;
import com.xinlan.yokirender.math.Vector4f;
import com.xinlan.yokirender.util.OpenglEsUtils;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 核心View 类
 */
public abstract class YokiView extends GLSurfaceView implements GLSurfaceView.Renderer {
    private static final String TAG = YokiView.class.getSimpleName();

    public enum REFRESH_MODE{
        WHEN_DIRTY,
        AUTO_REFRESH
    }

    /**
     * 用户自定义配置
     *
     */
    public static class YokiViewOptions{
        public REFRESH_MODE refreshMode = REFRESH_MODE.WHEN_DIRTY;//刷新模式 默认不自动刷新
        public boolean isLimitFps = true; //是否限制最高帧率
    }

    private Context mContext;
    public int viewWidth;
    public int viewHeight;

    private YokiViewOptions mOptions = null;

    private Vector4f mRefreshColor = new Vector4f();

    private YokiPaint mDefaultPaint;

    private YokiCanvasImpl mRender;

    private BitManager mBitManager;

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
        Log.d(TAG, "onDetachedFromWindow ");
        onDestory();
    }

    protected void init(Context context) {
        mContext = context;

        mOptions = new YokiViewOptions();
        initOptions(mOptions);

        setEGLContextClientVersion(3);
        setEGLConfigChooser(8, 8, 8, 8, 24, 0);

        //requestRender();
        setRenderer(this);
        if(mOptions.refreshMode == REFRESH_MODE.WHEN_DIRTY){
            setRenderMode(RENDERMODE_WHEN_DIRTY);
        }else {
            setRenderMode(RENDERMODE_CONTINUOUSLY);
        }
        mDefaultPaint = new YokiPaint();
        mBitManager = BitManager.newInstance();

        mRender = new YokiCanvasImpl(mDefaultPaint , mBitManager);
    }

    /**
     * 将位图资源导入GPU中
     *
     * @param resId
     * @return
     */
    public YokiBit loadBit(final int resId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId);
        return mBitManager.loadYokiBit(bitmap, true);
    }

    public YokiBit loadBitFromAssets(final String filepath) {
        InputStream inputStream = null;
        try {
            inputStream = getContext().getAssets().open(filepath);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return mBitManager.loadYokiBit(bitmap, true);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 手动请求刷新一次View
     */
    public void refreshView() {
        this.requestRender();
    }

    public abstract void initOptions(final YokiViewOptions options);

    public abstract void onInit(int width, int height);

    public abstract void onRender(final YokiCanvas canvas);

    /**
     * 设置背景颜色
     *
     * @param refreshColor
     */
    public void setRefreshColor(int refreshColor) {
        mRefreshColor = OpenglEsUtils.convertColor(refreshColor);
        //refreshView();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.d(TAG, "onSurfaceCreated");

        readGLConfig();
        mRender.initEngine(mContext);
    }

    /**
     *
     */
    private void readGLConfig() {
        final ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        int version = configurationInfo.reqGlEsVersion;
        String versionName = configurationInfo.getGlEsVersion();

        GLConfig.glVersion = version;
        GLConfig.glVersionName = versionName;

        Log.d(TAG, "GL version  " + Integer.toHexString(version) + " , " + versionName);

        float pointSizeBuffers[] = new float[2];
        GLES30.glGetFloatv(GLES30.GL_ALIASED_POINT_SIZE_RANGE, pointSizeBuffers, 0);
        GLConfig.maxPointSize = pointSizeBuffers[1];

        float lineWidthRangeBuffers[] = new float[2];
        GLES30.glGetFloatv(GLES30.GL_ALIASED_LINE_WIDTH_RANGE, lineWidthRangeBuffers, 0);
        GLConfig.maxLineWidth = lineWidthRangeBuffers[1];

        Log.d(TAG, "GL pointSizeRange  = " + pointSizeBuffers[0] + " - " + pointSizeBuffers[1]);
        Log.d(TAG, "GL lineWidthRange  = " + lineWidthRangeBuffers[0] + " - " + lineWidthRangeBuffers[1]);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.d(TAG, "onSurfaceChanged width = " + width + "  height = " + height);
        viewWidth = width;
        viewHeight = height;

        GLES30.glViewport(0, 0, width, height);
        GLES30.glEnable(GLES30.GL_DEPTH_TEST); //打开深度测试

        //开启blend混合方式  以正确渲染半透明的图片
        GLES30.glEnable(GLES30.GL_BLEND);
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA);

        mRender.onInitSurface(width, height);
        onInit(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //System.out.println("onDrawFrame");
        mRender.clearAllRender();

        long startRenderTime = System.currentTimeMillis();
        GLES30.glClearColor(mRefreshColor.x, mRefreshColor.y, mRefreshColor.z, mRefreshColor.w);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);

        final long t5 = System.currentTimeMillis();
        onRender(mRender);
        final long t6 = System.currentTimeMillis();
        Log.d(TAG, "view draw content time = " + (t6 - t5));

        final long t3 = System.currentTimeMillis();
        mRender.render();
        final long endRenderTime = System.currentTimeMillis();
        Log.d(TAG, "gl call time = " + (endRenderTime - t3));

        long frameCostTime = endRenderTime -startRenderTime;
        Log.d(TAG, "render a frame time = " + frameCostTime);

        if(mOptions.isLimitFps && frameCostTime < 40){ //保持画面帧数稳定在50帧
            try {
                Thread.sleep(40 - frameCostTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void onDestory() {
        if (mBitManager != null) {
            mBitManager.deleteAllBits();
        }
    }
}//end class
