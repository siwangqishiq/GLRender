package com.xinlan.yokirender.core.pool;

import com.xinlan.yokirender.core.command.RectCmd;

public class RectCmdPool extends BasePool<RectCmd> {

    public RectCmdPool(int initSize) {
        super(initSize);
    }

    @Override
    public RectCmd createNewInstance() {
        return RectCmd.newInstance();
    }
}
