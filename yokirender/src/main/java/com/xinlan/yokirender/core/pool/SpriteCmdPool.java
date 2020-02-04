package com.xinlan.yokirender.core.pool;

import com.xinlan.yokirender.core.command.SpriteCmd;

/**
 *  精灵渲染指令池
 */
public class SpriteCmdPool extends BasePool<SpriteCmd> {

    public SpriteCmdPool(int initSize) {
        super(initSize);
    }

    @Override
    public SpriteCmd createNewInstance() {
        return SpriteCmd.newInstance();
    }

}//end class
