package com.xinlan.yokirender.core.pool;

import com.xinlan.yokirender.core.command.Cmd;

import java.util.ArrayList;

/**
 * 渲染指令实例 base对象池
 *
 * @param <T>
 */
public abstract class BasePool<T extends Cmd> {
    private ArrayList<T> mObjList;

    protected int mNextIndex;

    public BasePool(int initSize) {
        mObjList = new ArrayList<T>(initSize + 1);
        for (int i = 0; i < initSize; i++) {
            mObjList.add(createNewInstance());
        }//end for i
        mNextIndex = 0;
    }

    public abstract T createNewInstance();

    public T obtain() {
        T result = null;

        int poolSize = mObjList.size();
        int i = mNextIndex;
        if(mNextIndex >= poolSize){
            mNextIndex = 0;
            i = 0;
        }

        do {
            if (mObjList.get(i).used) {//对象被使用 拿下一个
                i = (i + 1) % poolSize;
            } else {
                result = mObjList.get(i);
                result.used = true;
                mNextIndex = i + 1;
                break;
            }
        } while (i != mNextIndex);
        //System.out.println("mNextIndex = " + mNextIndex);

        if (result == null) {
            //System.out.println("!!! createNewInstance " + mNextIndex);
            result = createNewInstance();
        }
        return result;
        // return createNewInstance();
    }

}//end class
