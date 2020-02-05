package com.xinlan.yokirender.core;

import android.graphics.Bitmap;
import android.opengl.GLES30;
import android.opengl.GLUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *  统一管理位图操作
 *
 *   位图的载入 卸载
 *
 */
public class BitManager {

    private Map<Integer , YokiBit> mBits = new HashMap<Integer , YokiBit>();

    public static BitManager newInstance(){
        return new BitManager();
    }

    public YokiBit loadYokiBit(final Bitmap bitmap ,final boolean needRecycle){
        if(bitmap == null || bitmap.isRecycled())
            return null;

        int textureIds[] = new int[1];
        GLES30.glGenTextures(1 , textureIds , 0);
        int textureId = textureIds[0];

        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D  , textureId);

        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D , GLES30.GL_TEXTURE_MIN_FILTER , GLES30.GL_NEAREST);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D , GLES30.GL_TEXTURE_MAG_FILTER , GLES30.GL_LINEAR);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D , GLES30.GL_TEXTURE_WRAP_S , GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D , GLES30.GL_TEXTURE_WRAP_T , GLES30.GL_CLAMP_TO_EDGE);

        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D , 0 , bitmap , 0);

        final YokiBit bit = new YokiBit();
        bit.textureId = textureId;
        bit.srcWidth = bitmap.getWidth();
        bit.srcHeight = bitmap.getHeight();

        if(needRecycle){
            bitmap.recycle();
        }

        mBits.put(bit.textureId , bit);
        return bit;
    }

    public void deleteYokiBit(int textureId){
        int textures[] = new int[2];
        textures[0] = textureId;
        GLES30.glDeleteTextures(1 , textures, 0);

        mBits.remove(textureId);
    }
}//end class
