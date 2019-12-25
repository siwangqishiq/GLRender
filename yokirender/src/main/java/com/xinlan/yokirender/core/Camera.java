package com.xinlan.yokirender.core;

import android.opengl.Matrix;

/**
 * 虚拟摄像机  记录观察点的位置
 * 以此确定视口
 * <p>
 * 平移矩阵 x 缩放矩阵
 *
 *   世界坐标 -> 屏幕坐标
 *
 */
public class Camera {
    public float x;
    public float y;
    public float viewWidth;
    private float viewHeight;

    private float transformMatrix[] = new float[4 * 4]; //齐次平移矩阵
    private float scaleMatrix[] = new float[4 * 4]; //缩放矩阵
    //private float screenMatrix[] = new float[4*4]; //标准坐标向屏幕坐标转换

    private float mMatrix[] = new float[4 * 4];

    public Camera(float x, float y, float width, float height) {
        this.viewWidth = width;
        this.viewHeight = height;

//        this.x = x  -viewWidth / 2;
//        this.y = y -viewHeight / 2;
        this.x = x;
        this.y = y;

        resetMatrix();
    }

    private  void resetMatrix(){
        Matrix.setIdentityM(transformMatrix , 0);
        Matrix.setIdentityM(scaleMatrix , 0);
        //Matrix.setIdentityM(screenMatrix , 0);
        Matrix.setIdentityM(mMatrix , 0);

        Matrix.translateM(transformMatrix , 0 , -x , -y , 0);
        Matrix.scaleM(scaleMatrix , 0 , 1.0f / viewWidth , 1.0f / viewHeight , 1.0f );
        //Matrix.translateM(screenMatrix , 0 , -1 , -1 , 0);

        printM(mMatrix , 4);
        Matrix.multiplyMM(mMatrix , 0 , mMatrix , 0 , transformMatrix , 0);
        printM(mMatrix , 4);
        Matrix.multiplyMM(mMatrix , 0 , mMatrix , 0 , scaleMatrix , 0);
        printM(mMatrix , 4);
//        Matrix.multiplyMM(mMatrix , 0 , mMatrix , 0 , screenMatrix , 0);
//        printM(mMatrix , 4);
    }

    public void moveTo(float x , float y){
        this.x = x;
        this.y = y;
        resetMatrix();
    }

    public void moveBy(float dx , float dy){
        this.x += dx;
        this.y += dy;
        resetMatrix();
    }

    public float[] cameraMatrix() {
        return mMatrix;
    }

    public static float[] multiVec(float vec[] , float m[]){
        Matrix.multiplyMV(vec , 0 , m ,0 , vec, 0);
        return vec;
    }

    public static void printM(float[] m , int col){
        System.out.println("==============");
        for(int i = 0 ; i < m.length ; i ++){
            System.out.print(m[i]+"  ");
            if((i + 1) % col == 0){
                System.out.println();
            }
        }
    }

}//end class
