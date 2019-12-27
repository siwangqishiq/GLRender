package com.xinlan.yokirender.core.command;

import com.xinlan.yokirender.core.primitive.IRender;

public abstract  class Cmd implements IRender {
    public boolean used = false;

    @Override
    public void reset() {
        used = false;
    }
}
