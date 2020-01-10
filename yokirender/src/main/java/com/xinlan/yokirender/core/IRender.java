package com.xinlan.yokirender.core;

import com.xinlan.yokirender.math.Matrix3f;

/**
 *  图元渲染接口  实现此接口的 都是可以被渲染的图元
 */
public interface IRender {
    /**
     *
     */
    void init();

    /**
     *  实现render
     */
    void render(Matrix3f matrix);


    /**
     *
     */
    void reset();
}
