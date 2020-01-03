package com.xinlan.yokirender.core.pool;

import com.xinlan.yokirender.core.command.LineCmd;
import com.xinlan.yokirender.core.command.PointCmd;

public class LineCmdPool extends BasePool<LineCmd> {
    public LineCmdPool(int initSize) {
        super(initSize);
    }

    @Override
    public LineCmd createNewInstance() {
        return LineCmd.newInstance();
    }
}
