package com.xinlan.yokirender.core;

import com.xinlan.yokirender.core.math.Matrix3f;

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

    private Matrix3f mMatrix = new Matrix3f();
    private float[] result = new float[2];

    public Camera(float x, float y, float width, float height) {
        this.viewWidth = width;
        this.viewHeight = height;
        this.x = x;
        this.y = y;
        reset();
    }


    public void moveTo(float x , float y){
        this.x = x;
        this.y = y;
        reset();
    }

    public void moveBy(float dx , float dy){
        this.x += dx;
        this.y += dy;
        reset();
    }


    private void reset() {
        //mMatrix.setIdentity();
        mMatrix.m00 = 2 / viewWidth;
        mMatrix.m01 = 0f;
        mMatrix.m02 = (-2*x) / viewWidth - 1;

        mMatrix.m10 = 0f;
        mMatrix.m11 = 2/ viewHeight;
        mMatrix.m12 = (-2 * y) / viewHeight - 1;

        mMatrix.m20 = 0;
        mMatrix.m21 = 0;
        mMatrix.m22 = 1;
    }

    /**
     *  获得转换变化矩阵
     * @return
     */
    public Matrix3f getMatrix(){
        return mMatrix;
    }

    public float[] worldToScreen(float _x ,float _y){
        result[0] = 2 *(_x - x) / viewWidth - 1.0f;
        result[1] = 2 *(_y - y) / viewHeight - 1.0f;
        return result;
    }

    public static float[] multiVec(float vec[] , float m[]){
        //Matrix.multiplyMV(vec , 0 , m ,0 , vec, 0);
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
