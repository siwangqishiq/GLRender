package com.xinlan.yokirender.core.pool;

import com.xinlan.yokirender.core.command.LineCmd;
import com.xinlan.yokirender.core.command.PointCmd;
import com.xinlan.yokirender.core.command.RectCmd;
import com.xinlan.yokirender.core.command.TriangleCmd;

/**
 *  command池
 *
 *   用于存贮渲染指令 以便于复用
 *
 */
public class CmdPools {
    private static final int INIT_SIZE = 32;

    private PointCmdPool mPointCmdPool;
    private LineCmdPool mLineCmdPool;
    private TriangleCmdPool mTriangleCmdPool;
    private RectCmdPool mRectCmdPool;

    public CmdPools(){
    }

    public void initCmds(){
        mPointCmdPool = new PointCmdPool(INIT_SIZE);
        mLineCmdPool = new LineCmdPool(INIT_SIZE);
        mTriangleCmdPool = new TriangleCmdPool(16);
        mRectCmdPool = new RectCmdPool(INIT_SIZE);
    }

    public PointCmd obtainPointCmd(){
       return mPointCmdPool.obtain(0);
    }


    public LineCmd obtainLineCmd() {
        return mLineCmdPool.obtain();
    }

    public TriangleCmd obtainTriangleCmd(){
        return mTriangleCmdPool.obtain();
    }

    public RectCmd obtainRectCmd(){
        return mRectCmdPool.obtain();
    }
}//end class
