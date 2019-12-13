package com.xinlan.yokirender.core;

import android.graphics.Color;

/**
 * 绘制画笔信息
 */
public class YokiPaint {
    enum Style{
        POINT,
        LINE,
        FILLED,
    }

    public int color = Color.BLACK;//画笔颜色
    public Style style = Style.LINE;
    public int lineWidth = 1;
    public int pointSize = 1;

    public YokiPaint() {
    }
}//end class
