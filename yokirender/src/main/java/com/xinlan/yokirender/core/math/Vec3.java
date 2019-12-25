package com.xinlan.yokirender.core.math;

public final class Vec3 {
    public float x;
    public float y;
    public float z;

    public Vec3() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Vec3(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public Vec3(float[] data) {
        this.x = data[0];
        this.y = data[1];
        this.z = data[2];
    }
}// end class
