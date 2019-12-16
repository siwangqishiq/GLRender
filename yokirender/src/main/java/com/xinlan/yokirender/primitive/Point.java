package com.xinlan.yokirender.primitive;

import com.xinlan.yokirender.core.Vec3;

/**
 * 绘制点图元
 */
public class Point implements IRender {

    public static Point obtain(){
        return new Point();
    }



    //坐标
    public float x;
    public float y;

    //尺寸
    public float size;

    //颜色
    public Vec3 color;

    public Point(){
    }

    @Override
    public void render() {

    }
}
