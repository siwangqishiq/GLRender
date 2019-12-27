package com.xinlan.yokirender.core;

import com.xinlan.yokirender.core.math.Color4f;

/**
 * 绘制画笔信息
 */
public class YokiPaint {
    enum Style{
        POINT,
        LINE,
        FILLED,
    }

   public Color4f color = new Color4f(0 , 0 ,0 ,0);
    public Style style = Style.LINE;
    public int lineWidth = 1;
    public int pointSize = 1;

    public YokiPaint() {
    }

}//end class
