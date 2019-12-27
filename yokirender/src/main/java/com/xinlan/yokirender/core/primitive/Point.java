package com.xinlan.yokirender.core.primitive;

import android.opengl.GLES30;

import com.xinlan.yokirender.core.math.Color4f;
import com.xinlan.yokirender.util.OpenglEsUtils;
import com.xinlan.yokirender.util.ShaderUtil;

/**
 * 绘制点图元
 */
public class Point extends BPrimitive {

    public static Point obtain(){
        return new Point();
    }

    public Point(){

    }

    public void init() {
        //programId = ShaderUtil.buildShaderProgram();
    }

    public void render(float x , float y , float size , Color4f color){
        //GLES30.glUseProgram();
    }
} //end class
