package com.xinlan.yokirender.core.primitive;

import android.text.TextUtils;

import com.xinlan.yokirender.util.OpenglEsUtils;
import com.xinlan.yokirender.util.ShaderUtil;

import java.util.HashMap;

/**
 *  统一处理shader 代码的导入
 *
 */
public class Shader {
    private static Shader mInstance;

    /**
     *  shader name -> programId
     */
    private HashMap<String , Integer> shaderNameToPrograms = new HashMap<String , Integer>();

    public static Shader getInstance() {
        if(mInstance == null) {
            mInstance = new Shader();
        }

        return mInstance;
    }

    private Shader(){
    }

    private void initShader(){

    }

    /**
     * 顶点着色器代码 + 片段着色器代码
     * @param vertetxSrc
     * @param frgSrc
     * @return
     */
    public int loadShader(String programName , String vertetxSrc , String frgSrc) {
        int programId = ShaderUtil.buildShaderProgram(vertetxSrc , vertetxSrc);
        shaderNameToPrograms.put(programName , programId);
        return programId;
    }

    public int findProgramId(String programName) {
        if(TextUtils.isEmpty(programName))
            return -1;

        Integer ret = shaderNameToPrograms.get(programName);
        return ret != null?ret.intValue() : -1;
    }
}
