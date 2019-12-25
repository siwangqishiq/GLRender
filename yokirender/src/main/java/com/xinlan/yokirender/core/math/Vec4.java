package com.xinlan.yokirender.core.math;

public final class Vec4 {
    public float x;
    public float y;
    public float z;
    public float w;

    public Vec4() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.w = 1;
    }

    public Vec4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }


    public Vec4(float[] data) {
        this.x = data[0];
        this.y = data[1];
        this.z = data[2];
        this.w = data[3];
    }
}// end class
