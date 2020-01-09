package com.xinlan.yokirender.core.pool;

import com.xinlan.yokirender.core.command.TriangleCmd;

/**
 *   三角形图元
 */
public class TriangleCmdPool extends BasePool<TriangleCmd> {
    public TriangleCmdPool(int initSize) {
        super(initSize);
    }

    @Override
    public TriangleCmd createNewInstance() {
        return TriangleCmd.newInstance();
    }

    @Override
    public TriangleCmd obtain() {
        TriangleCmd cmd = null;

        for(int i = 0,len = mObjList.size() ; i < len;i++){
            if(!mObjList.get(i).isFull){
                cmd = mObjList.get(i);
                break;
            }
        }
        if(cmd == null){
            cmd = createNewInstance();
            mObjList.add(cmd);
        }
        return cmd;
    }
}
