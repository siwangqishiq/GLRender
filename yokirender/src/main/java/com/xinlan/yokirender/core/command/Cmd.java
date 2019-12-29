package com.xinlan.yokirender.core.command;

import com.xinlan.yokirender.core.YokiPaint;
import com.xinlan.yokirender.core.primitive.IRender;

public abstract  class Cmd implements IRender {
    public int zorder; //层叠顺序
    public YokiPaint paint;
    //尺寸
    public float size;

    public int programId; //着色器程序索引

    public boolean used = false;

    @Override
    public void reset() {
        used = false;
    }
}
