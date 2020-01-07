package com.xinlan.yokirender.core.pool;

import com.xinlan.yokirender.core.command.PointCmd;

public class PointCmdPool extends BasePool<PointCmd> {
    public PointCmdPool(int initSize) {
        super(initSize);
    }

    @Override
    public PointCmd createNewInstance() {
        return PointCmd.newInstance();
    }

    public PointCmd obtain(int val) {
        PointCmd cmd = null;

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
