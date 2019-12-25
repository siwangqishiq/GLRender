package com.xinlan.yokirender.core;

import org.junit.Test;

public class CameraTest {
    @Test
    public void testCamera() {
        Camera camera = new Camera(0, 0, 100, 100);

        float m[] = camera.cameraMatrix();
        float[] v1 = {50, 50, 0, 0};
        v1 = Camera.multiVec(v1 , m);
        for(int i = 0 ; i < v1.length ; i++){
            System.out.print(v1[i]);
        }
        System.out.println();
    }
}//end class
