package com.xinlan.yokirender.core.command;

import com.xinlan.yokirender.core.math.Matrix3f;

/**
 * 点 render 指令
 */
public class PointCmd extends Cmd {
    private float data[] = new float[2];
    private float color[] = new float[4];
    private float size = 1.0f;

    public static String vertexShaderSrc() {
        return "#version es 230"
                ;
    }

    public static String fragShaderSrc(){
        return "";
    }

    public static PointCmd newInstance() {
        return new PointCmd();
    }

    public void init(){

    }

    @Override
    public void render(Matrix3f matrix) {

    }

    public void reset(float x , float y) {

    }
}
