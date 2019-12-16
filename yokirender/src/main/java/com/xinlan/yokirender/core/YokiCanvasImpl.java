package com.xinlan.yokirender.core;

import com.xinlan.yokirender.primitive.IRender;

import java.util.ArrayList;

public class YokiCanvasImpl implements YokiCanvas {
    private YokiPaint mDefaultPaint;
    private ArrayList<IRender> mRenderList = new ArrayList<IRender>(64);

    public YokiCanvasImpl(YokiPaint paint){
        mDefaultPaint = paint;
    }

    public void onInitSurface(int w , int h){

    }

    public void render(){
        //1 .视景体的剔除
        // 2. render
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
