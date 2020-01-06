package com.xinlan.yokirender.core.shader;

import android.content.Context;
import android.text.TextUtils;

import com.xinlan.yokirender.R;
import com.xinlan.yokirender.core.command.LineCmd;
import com.xinlan.yokirender.core.command.PointCmd;
import com.xinlan.yokirender.core.command.TriangleCmd;
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
    }

    public void initShader(Context ctx){
        loadShader(ctx , PointCmd.RENDER_POINT , R.raw.render_point_vertex , R.raw.render_point_fragment);
        loadShader(ctx , LineCmd.RENDER_LINE , R.raw.render_line_vertex , R.raw.render_line_fragment);
        loadShader(ctx , TriangleCmd.RENDER_TRIANGLE , R.raw.render_triangle_vertex , R.raw.render_triangle_fragment);
    }


    public int loadShader(Context ctx , String programName , int vertetxSrcResId , int frgSrcResId) {
        return loadShader(programName , getSrc(ctx , vertetxSrcResId) , getSrc(ctx , frgSrcResId));
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
    public static String getSrc(Context context , int resId) {
        if(context == null)
            return null;

        return ShaderUtil.readTextFileFromRaw(context , resId);
    }
}
