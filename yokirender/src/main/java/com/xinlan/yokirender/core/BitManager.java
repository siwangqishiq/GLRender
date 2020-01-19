package com.xinlan.yokirender.core;

import android.graphics.Bitmap;
import android.opengl.GLES30;

/**
 *  统一管理位图操作
 *
 *   位图的载入 卸载
 *
 */
public class BitManager {
    public static BitManager newInstance(){
        return new BitManager();
    }

    public YokiBit loadYokiBit(final Bitmap bitmap ,final boolean needRecycle){
        if(bitmap == null || bitmap.isRecycled())
            return null;

        int textureIds[] = new int[1];
        GLES30.glGenTextures(1 , textureIds , 0);

        if(needRecycle){
            bitmap.recycle();
        }
        return null;
    }

    public void deleteYokiBit(){

    }
}//end class
