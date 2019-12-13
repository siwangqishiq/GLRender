package com.xinlan.yokirender.core;

public interface YokiCanvas {

    /**
     *  绘制顶点
     * @param x
     * @param y
     * @param paint
     */
    void drawPoint(float x , float y  , YokiPaint paint);

    /**
     * 绘制线条
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param paint
     */
    void drawLine(float x1 , float y1 , float x2 , float y2 , YokiPaint paint);

    /**
     * 绘制三角形
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param x3
     * @param y3
     * @param paint
     */
    void drawTriangle(float x1 , float y1 , float x2 , float y2 , float x3 , float y3 , YokiPaint paint);

    /**
     * 绘制矩形
     * @param left
     * @param top
     * @param width
     * @param height
     * @param paint
     */
    void drawRect(float left , float top , float width ,float height , YokiPaint paint);

    void save();

    void rotate(float degree , float centerX , float centerY);

    void scale(float scaleX , float scaleY);

    void transform(float deltaX , float deltaY);

    void restore();
}
