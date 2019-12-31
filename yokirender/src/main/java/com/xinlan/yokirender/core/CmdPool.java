package com.xinlan.yokirender.core;

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
public class CmdPool {
    private static final int INIT_SIZE = 50;

    private int pointINextIndex;
    private LinkedList<PointCmd> pointsPool = new LinkedList<PointCmd>();//画点指令集

    public CmdPool(){
    }

    public void initCmds(){
        initPointCmds(INIT_SIZE);
    }

    private void initPointCmds(int initSize) {
        pointINextIndex = 0;
        for(int i = 0 ; i < initSize;i++) {
            pointsPool.add(PointCmd.newInstance());
        }//end for i
    }

    public PointCmd obtainPointCmd(){
        PointCmd cmd = null;
        int startIndex = pointINextIndex;
        do{
            if(pointINextIndex >= pointsPool.size()){
                pointINextIndex = 0;
            }

            if(!pointsPool.get(pointINextIndex).used){ //找到一个没有被使用过的对象
                cmd = pointsPool.get(pointINextIndex);
                break;
            }else{
                pointINextIndex++;
            }
        }while (pointINextIndex != startIndex);

        if(cmd == null){
            cmd = PointCmd.newInstance();
            // pointsPool.add(cmd);
        }
        //System.out.println("pointINextIndex  = " + pointINextIndex);
        cmd.used = true;
        return cmd;
    }

}//end class
