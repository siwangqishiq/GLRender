package com.xinlan.yokirender.primitive;

import com.xinlan.yokirender.core.math.Vector2f;

/**
 * 绘制点图元
 */
public class Point extends BPrimitive {

    public static Point obtain(){
        return new Point();
    }

    //颜色
    public Vector2f color;

    public Point(){

    }

    @Override
    public void render() {

    }
}
