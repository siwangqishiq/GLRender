package com.xinlan.yokirender.util;

import android.graphics.Color;
import android.util.Log;

import com.xinlan.yokirender.math.Vector4f;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class OpenglEsUtils {
    private static final String TAG = "OpenglEsUtils";

    public static long framePerSecond = 0;
    public static long lastTime = 0;

    public static void debugFps() {
        framePerSecond++;
        long curTime = System.currentTimeMillis();
        if (curTime - lastTime >= 1000) {
            lastTime = curTime;
            Log.d(TAG, "fps = " + framePerSecond);
            //System.out.println("fps = " +framePerSecond);
            framePerSecond = 0;
        }
    }

    public static FloatBuffer allocateBuf(float array[]) {
        ByteBuffer bb = ByteBuffer.allocateDirect(array.length * Float.BYTES)
                .order(ByteOrder.nativeOrder());
        FloatBuffer buf = bb.asFloatBuffer();
        buf.put(array);
        buf.position(0);
        return buf;
    }

    public static FloatBuffer allocateBuf(float value) {
        ByteBuffer bb = ByteBuffer.allocateDirect(Float.BYTES)
                .order(ByteOrder.nativeOrder());
        FloatBuffer buf = bb.asFloatBuffer();
        buf.put(value);
        buf.position(0);
        return buf;
    }

    public static FloatBuffer allocateBufBySize(int size) {
        ByteBuffer bb = ByteBuffer.allocateDirect(size * Float.BYTES)
                .order(ByteOrder.nativeOrder());
        FloatBuffer buf = bb.asFloatBuffer();
        buf.position(0);
        return buf;
    }

    public static ByteBuffer allocateBuf(byte array[]) {
        ByteBuffer bb = ByteBuffer.allocateDirect(array.length * Byte.BYTES)
                .order(ByteOrder.nativeOrder());
        bb.put(array);
        bb.position(0);
        return bb;
    }

    /**
     *  native heap分配内存
     * @param size
     * @return
     */
    public static IntBuffer allocateIntBuf(final int size){
        ByteBuffer bb = ByteBuffer.allocateDirect(size * Integer.BYTES)
                .order(ByteOrder.nativeOrder());
        IntBuffer buf = bb.asIntBuffer();
        buf.position(0);
        return buf;
    }

    /**
     *  native heap分配内存
     * @param size
     * @return
     */
    public static ByteBuffer allocateBuf(final int size){
        ByteBuffer buf = ByteBuffer.allocateDirect(size)
                .order(ByteOrder.nativeOrder());
        buf.position(0);
        return buf;
    }


    public static Vector4f convertColor(int color) {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        int a = Color.alpha(color);

        float[] cs = convertColor(r, g, b, a);
        return new Vector4f(cs);
    }

    public static float[] convertColor(float r, float g, float b, int a) {
        float[] colors = new float[4];
        colors[0] = clamp(0.0f, 1.0f, r / 255);
        colors[1] = clamp(0.0f, 1.0f, g / 255);
        colors[2] = clamp(0.0f, 1.0f, b / 255);
        colors[3] = clamp(0.0f, 1.0f, a / 255);
        return colors;
    }

    public static void convertColor(float r, float g, float b, float a, float[] colors) {
        colors[0] = clamp(0.0f, 1.0f, r / 255);
        colors[1] = clamp(0.0f, 1.0f, g / 255);
        colors[2] = clamp(0.0f, 1.0f, b / 255);
        colors[3] = clamp(0.0f, 1.0f, a / 255);
    }

    public static float clamp(float min, float max, float v) {
        if (v <= min)
            return min;
        if (v >= max)
            return max;
        return v;
    }

//    public static void debugPrintMat(float[] m) {
//        System.out.println(m[0] + " " +m[1] +" " + m[2] +" " + m[3]);
//        System.out.println(m[4] + " " +m[5] +" " + m[6] +" " + m[7]);
//        System.out.println(m[8] + " " +m[9] +" " + m[10] +" " + m[11]);
//        System.out.println(m[12] + " " +m[13] +" " + m[14] +" " + m[15]);
//    }
}
