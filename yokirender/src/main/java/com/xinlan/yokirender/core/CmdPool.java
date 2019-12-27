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
    private List<PointCmd> pointsPool = new LinkedList<PointCmd>();//画点指令集

    public CmdPool(){
    }

    public void initCmds(){
        initPointCmds();
    }

    private void initPointCmds() {
        pointINextIndex = 0;

    }

    public PointCmd obtainPointCmd(float x , float y , YokiPaint paint){
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

        if(cmd != null){

        }else {

        }
        return cmd;
    }

}//end class
