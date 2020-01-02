package com.xinlan.yokirender.core.pool;

import com.xinlan.yokirender.core.command.PointCmd;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *  command池
 *
 *   用于存贮渲染指令 以便于复用
 *
 */
public class CmdPools {
    private static final int INIT_SIZE = 50;

    private PointPool mPointPool;

    public CmdPools(){
    }

    public void initCmds(){
        mPointPool = new PointPool(INIT_SIZE);
    }

    public PointCmd obtainPointCmd(){
       return mPointPool.obtain();
    }

}//end class
