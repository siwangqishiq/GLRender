package com.xinlan.yokirender.core.primitive;

import android.content.Context;
import android.text.TextUtils;

import com.xinlan.yokirender.core.command.PointCmd;
import com.xinlan.yokirender.util.ShaderUtil;

import java.util.HashMap;

/**
 *  统一处理shader 代码的导入
 *
 */
public class ShaderManager {
    private static ShaderManager mInstance;
    public static Context ctx;

    /**
     *  shader name -> programId
     */
    private HashMap<String , Integer> shaderNameToPrograms = new HashMap<String , Integer>();

    public static ShaderManager getInstance() {
        if(mInstance == null) {
            mInstance = new ShaderManager();
        }

        return mInstance;
    }

    private ShaderManager(){
        initShader();
    }

    private void initShader(){
        loadShader(PointCmd.RENDER_POINT , PointCmd.vertexShaderSrc() , PointCmd.fragShaderSrc());
    }

    /**
     * 顶点着色器代码 + 片段着色器代码
     * @param vertetxSrc
     * @param frgSrc
     * @return
     */
    public int loadShader(String programName , String vertetxSrc , String frgSrc) {
        int programId = ShaderUtil.buildShaderProgram(vertetxSrc , frgSrc);
        shaderNameToPrograms.put(programName , programId);
        return programId;
    }

    public int findProgramId(String programName) {
        if(TextUtils.isEmpty(programName))
            return -1;

        Integer ret = shaderNameToPrograms.get(programName);
        return ret != null?ret.intValue() : -1;
    }

    /**
     * 从资源ID获取字符串源码
     *
     * @param resId
     * @return
     */
    public static String getSrc(int resId) {
        if(ctx == null)
            return null;

        return ShaderUtil.readTextFileFromRaw(ctx , resId);
    }
}
