package com.xinlan.yokirender.core.pool;

import com.xinlan.yokirender.core.command.TriangleCmd;

public class TriangleCmdPool extends BasePool<TriangleCmd> {
    public TriangleCmdPool(int initSize) {
        super(initSize);
    }

    @Override
    public TriangleCmd createNewInstance() {
        return TriangleCmd.newInstance();
    }
}
