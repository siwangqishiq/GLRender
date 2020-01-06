package com.xinlan.yokirender.core.pool;

import com.xinlan.yokirender.core.command.LineCmd;
import com.xinlan.yokirender.core.command.PointCmd;

/**
 *  command池
 *
 *   用于存贮渲染指令 以便于复用
 *
 */
public class CmdPools {
    private static final int INIT_SIZE = 100;

    private PointCmdPool mPointCmdPool;
    private LineCmdPool mLineCmdPool;

    public CmdPools(){
    }

    public void initCmds(){
        mPointCmdPool = new PointCmdPool(INIT_SIZE);
        mLineCmdPool = new LineCmdPool(INIT_SIZE);
    }

    public PointCmd obtainPointCmd(){
       return mPointCmdPool.obtain();
    }


    public LineCmd obtainLineCmd() {
        return mLineCmdPool.obtain();
    }
}//end class
