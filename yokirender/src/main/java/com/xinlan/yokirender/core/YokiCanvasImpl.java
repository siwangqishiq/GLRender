package com.xinlan.yokirender.core;

public class YokiCanvasImpl implements YokiCanvas {
    private YokiPaint mDefaultPaint;

    public YokiCanvasImpl(YokiPaint paint){
        mDefaultPaint = paint;
    }

    public void onInitSurface(int w , int h){

    }

    public void render(){

    }

    @Override
    public void drawPoint(float x, float y, YokiPaint paint) {

    }

    @Override
    public void drawLine(float x1, float y1, float x2, float y2, YokiPaint paint) {

    }

    @Override
    public void drawTriangle(float x1, float y1, float x2, float y2, float x3, float y3, YokiPaint paint) {

    }

    @Override
    public void drawRect(float left, float top, float width, float height, YokiPaint paint) {

    }

    @Override
    public void save() {

    }

    @Override
    public void rotate(float degree, float centerX, float centerY) {

    }

    @Override
    public void scale(float scaleX, float scaleY) {

    }

    @Override
    public void transform(float deltaX, float deltaY) {

    }

    @Override
    public void restore() {

    }


}//end class
