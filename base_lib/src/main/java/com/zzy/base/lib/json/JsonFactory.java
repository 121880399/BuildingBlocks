package com.zzy.base.lib.json;

/**
 * @function Json工具工程类，采用单利模式，用户不必在意使用了Gson还是FastJson。
 * Created by zhou on 2018/5/3.
 */

public class JsonFactory {

    private static JsonUtils jsonUtils;

    public static JsonUtils getJsonUtils(){
        if(jsonUtils==null){
            synchronized (JsonFactory.class){
                if(jsonUtils==null){
                    jsonUtils=new FastJsonUtils();
                }
            }
        }
        return jsonUtils;
    }

}
