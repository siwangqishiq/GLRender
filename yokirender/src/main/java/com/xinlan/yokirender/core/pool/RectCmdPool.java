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

    @Override
    public RectCmd obtain() {
        RectCmd cmd = null;

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
