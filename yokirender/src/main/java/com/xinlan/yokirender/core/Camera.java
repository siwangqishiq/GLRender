package com.xinlan.yokirender.core;

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

//    private float transformMatrix[] = new float[4 * 4]; //齐次平移矩阵
//    private float scaleMatrix[] = new float[4 * 4]; //缩放矩阵
//    //private float screenMatrix[] = new float[4*4]; //标准坐标向屏幕坐标转换
//
//    private float mMatrix[] = new float[4 * 4];

    private float[] result = new float[2];

    public Camera(float x, float y, float width, float height) {
        this.viewWidth = width;
        this.viewHeight = height;

//        this.x = x  -viewWidth / 2;
//        this.y = y -viewHeight / 2;
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
