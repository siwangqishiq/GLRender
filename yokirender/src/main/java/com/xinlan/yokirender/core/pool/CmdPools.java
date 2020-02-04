package com.xinlan.yokirender.core.pool;

import com.xinlan.yokirender.core.command.CircleCmd;
import com.xinlan.yokirender.core.command.LineCmd;
import com.xinlan.yokirender.core.command.PointCmd;
import com.xinlan.yokirender.core.command.RectCmd;
import com.xinlan.yokirender.core.command.SpriteCmd;
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
    private CircleCmdPool mCircleCmdPool;

    //精灵渲染指令池
    private SpriteCmdPool mSpriteCmdPool;

    public CmdPools(){
    }

    public void initCmds(){
        mPointCmdPool = new PointCmdPool(INIT_SIZE);
        mLineCmdPool = new LineCmdPool(INIT_SIZE);
        mTriangleCmdPool = new TriangleCmdPool(INIT_SIZE);
        mRectCmdPool = new RectCmdPool(INIT_SIZE);
        mCircleCmdPool = new CircleCmdPool(INIT_SIZE);

        mSpriteCmdPool = new SpriteCmdPool(INIT_SIZE >> 2);
    }

    public PointCmd obtainPointCmd(){
       return mPointCmdPool.obtainForBatch();
    }

    public LineCmd obtainLineCmd() {
        return mLineCmdPool.obtainForBatch();
    }

    public TriangleCmd obtainTriangleCmd(){
        return mTriangleCmdPool.obtainForBatch();
    }

    public RectCmd obtainRectCmd(){
        return mRectCmdPool.obtainForBatch();
    }

    public CircleCmd obtainCircleCmd() {
        return mCircleCmdPool.obtainForBatch();
    }

    public SpriteCmd obtainSpriteCmd() {
        return mSpriteCmdPool.obtain();
    }
}//end class
