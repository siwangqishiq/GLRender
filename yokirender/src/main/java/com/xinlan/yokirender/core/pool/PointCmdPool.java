package com.xinlan.yokirender.core.pool;

import com.xinlan.yokirender.core.command.PointCmd;

public class PointCmdPool extends BasePool<PointCmd> {
    public PointCmdPool(int initSize) {
        super(initSize);
    }

    @Override
    public PointCmd createNewInstance() {
        return PointCmd.newInstance();
    }
}
