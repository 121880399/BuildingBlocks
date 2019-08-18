package com.zzy.base.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by zhou on 2018/5/3.
 */

public class GsonUtils implements JsonUtils {

    private Gson gson;

    public GsonUtils() {
        this.gson = new Gson();
    }

    @Override
    public JSONObject parseObject(String jsonStr) {
        JSONObject obj=null;
        try {
            obj= new JSONObject(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public <T> T parseObject(String jsonStr, Class<T> clazz) {
        return  gson.fromJson(jsonStr,clazz);
    }

    @Override
    public JSONArray parseArray(String jsonStr) {
        JSONArray jsonArray=null;
        try {
            jsonArray=new JSONArray(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    @Override
    public <T> List<T> parseArray(String jsonStr, Class<T> clazz) {
        Type type=new TypeToken<List<T>>(){}.getType();
        return gson.fromJson(jsonStr,type);
    }

    @Override
    public String toJson(Object obj) {
        return gson.toJson(obj);
    }
}
