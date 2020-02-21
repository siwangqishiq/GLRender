package com.xinlan.yokirender.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.opengl.GLES30;
import android.opengl.GLUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一管理位图操作
 * <p>
 * 位图的载入 卸载
 */
public class BitManager {

    private Map<Integer, YokiBit> mBits = new HashMap<Integer, YokiBit>();

    private HashMap<String, YokiBit> mTextBits = new HashMap<String, YokiBit>();
    private Paint mTextPaint = new Paint(); //文本Paint

    public static BitManager newInstance() {
        return new BitManager();
    }

    public BitManager() {
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(32.0f);
        mTextPaint.setAntiAlias(true);
    }

    /**
     * 对文本位图做提前生成
     *
     * @param textContent
     * @return
     */
    public YokiBit getTextBit(final String textContent) {
        YokiBit result = mTextBits.get(textContent);
        if (result != null) {
            return result;
        }

        result = genTextBitmap(textContent);
        mTextBits.put(textContent, result);
        return result;
    }

    public YokiBit genTextBitmap(final String text) {
        final Rect outRect = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), outRect);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        //todo RGBA8888没必要 先这么设置 后面优化
        final Bitmap bit = Bitmap.createBitmap(outRect.width(), (int)(fontMetrics.bottom - fontMetrics.top), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bit);
        canvas.drawText(text, 0, -fontMetrics.ascent, mTextPaint);
        return loadYokiBit(bit, true);
    }

    public YokiBit loadYokiBit(final Bitmap bitmap, final boolean needRecycle) {
        return loadYokiBit(bitmap, needRecycle, false);
    }

    public YokiBit loadYokiBit(final Bitmap bitmap, final boolean needRecycle, final boolean needVertexFlip) {
        if (bitmap == null || bitmap.isRecycled())
            return null;

        int textureIds[] = new int[1];
        GLES30.glGenTextures(1, textureIds, 0);
        int textureId = textureIds[0];

        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId);

        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);

        if (needVertexFlip) {
            Bitmap flipBit = convertBitmap(bitmap);
            GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, flipBit, 0);
            flipBit.recycle();
        } else {
            GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0);
        }

        final YokiBit bit = new YokiBit();
        bit.textureId = textureId;
        bit.srcWidth = bitmap.getWidth();
        bit.srcHeight = bitmap.getHeight();

        if (needRecycle) {
            bitmap.recycle();
        }

        mBits.put(bit.textureId, bit);
        return bit;
    }

    private static Bitmap convertBitmap(Bitmap bit) {
        final Bitmap newb = Bitmap.createBitmap(bit.getWidth(), bit.getHeight(), bit.getConfig());
        Canvas cv = new Canvas(newb);
        Matrix m = new Matrix();
        m.postScale(1, -1);   //镜像垂直翻转
        //m.postScale(-1, 1);   //镜像水平翻转
        //m.postRotate(-90);  //旋转-90度
        Bitmap new2 = Bitmap.createBitmap(bit, 0, 0, bit.getWidth(), bit.getHeight(), m, true);
        Rect rect = new Rect(0, 0, bit.getWidth(), bit.getHeight());
        cv.drawBitmap(new2, rect, rect, null);
        return newb;
    }

    public void deleteYokiBit(int textureId, boolean removeSet) {
        int textures[] = new int[2];
        textures[0] = textureId;
        GLES30.glDeleteTextures(1, textures, 0);

        if (removeSet) {
            mBits.remove(textureId);
        }
    }

    /**
     * 释放所有纹理显存
     */
    public void deleteAllBits() {
        if (!mBits.isEmpty()) {
            for (Integer texID : mBits.keySet()) {
                if (texID.intValue() >= 0) {
                    deleteYokiBit(texID.intValue(), false);
                }
            }//end for each
            mBits.clear();
        }
    }
}//end class
