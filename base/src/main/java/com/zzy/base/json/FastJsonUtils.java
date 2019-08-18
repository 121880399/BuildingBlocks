package com.zzy.base.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @function 使用FastJson实现
 * Created by zhou on 2018/5/4.
 */

public class FastJsonUtils implements JsonUtils {


    @Override
    public JSONObject parseObject(String jsonStr) {
        return JSON.parseObject(jsonStr);
    }

    @Override
    public <T> T parseObject(String jsonStr, Class<T> clazz) {
        return JSON.parseObject(jsonStr, clazz);
    }

    @Override
    public JSONArray parseArray(String jsonStr) {
        return JSON.parseArray(jsonStr);
    }

    @Override
    public <T> List<T> parseArray(String jsonStr, Class<T> clazz) {
        return JSON.parseArray(jsonStr, clazz);
    }

    @Override
    public String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }
}
