package com.xinlan.yokirender.core.pool;

import com.xinlan.yokirender.core.command.PointCmd;

public class PointPool extends BasePool<PointCmd> {
    public PointPool(int initSize) {
        super(initSize);
    }

    @Override
    public PointCmd createNewInstance() {
        return PointCmd.newInstance();
    }
}
