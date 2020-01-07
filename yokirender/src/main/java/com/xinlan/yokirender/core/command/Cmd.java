package com.xinlan.yokirender.core.command;

import com.xinlan.yokirender.core.YokiPaint;
import com.xinlan.yokirender.core.IRender;
import com.xinlan.yokirender.util.OpenglEsUtils;

import java.nio.FloatBuffer;

public abstract  class Cmd implements IRender {
    public int zorder; //层叠顺序
    public YokiPaint mPaint;
    //尺寸
    public float size;

    public int mProgramId; //着色器程序索引
    public int mUniformMatrixLoc; //shader中基础变换矩阵的引用

    public boolean used = false;
    public boolean addRenderList = false;//

    public boolean isFull = false;
    public int mIndex = 0;

    @Override
    public void reset() {
        used = false;
        addRenderList = false;
        isFull = false;
        mIndex = 0;
    }

    public static FloatBuffer allocateFloatBufBySize(final int size) {
        return OpenglEsUtils.allocateBufBySize(size);
    }

}
