package com.xinlan.yokirender.core.pool;

import com.xinlan.yokirender.core.command.CircleCmd;

public class CircleCmdPool extends BasePool<CircleCmd> {

    public CircleCmdPool(int initSize) {
        super(initSize);
    }

    @Override
    public CircleCmd createNewInstance() {
        return CircleCmd.newInstance();
    }
}
