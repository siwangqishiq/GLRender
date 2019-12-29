package com.xinlan.yokirender.core.primitive;

import com.xinlan.yokirender.core.YokiPaint;

public abstract class BasePrimitive {
    public float x,y;
    public int zorder; //层叠顺序
    public YokiPaint paint;
    //尺寸
    public float size;

    public int programId; //着色器程序索引
}
