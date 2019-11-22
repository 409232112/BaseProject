package com.wyc.core.util;

import com.wyc.core.base.exception.BaseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonUtility {

    private static ObjectMapper objectMapper;

    static{
        objectMapper = new ObjectMapper();
    }

    static public JSONObject convertBeanToJsonObject(Object bean) throws BaseException {
        if (bean == null) {
            return new JSONObject();
        }
        try {
            return new JSONObject(objectMapper.writeValueAsString(bean));
        } catch (JSONException e) {
            throw new BaseException(e);
        } catch (IOException e) {
            throw new BaseException(e);
        }

    }

    static public JSONArray convertBeanToJsonArray(Object bean) throws BaseException {
        if (bean == null) {
            return new JSONArray();
        }
        try {
            return new JSONArray(new String(objectMapper.writeValueAsBytes(bean), "UTF-8"));
        } catch (Exception e) {
            throw new BaseException(-1, "服务器内部错误", e);
        }
    }


    public static String constructResultJson(String code, Object message) throws BaseException {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("code", code);
            jsonObject.put("message", message);
        } catch (JSONException e) {
            throw new BaseException(-1, "服务器内部错误", e);
        }
        return jsonObject.toString();
    }

    public static String constructResultJson(String code, Object message,Map map) throws BaseException {
        return CommonUtility.constructResultJsonAsString(code,message,map);
    }

    public static JSONObject constructFailureJson(int code) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("code", code);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return jsonObject;
    }

    public static String constructResultJsonAsString(String code, Object message, Object data) throws BaseException {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("code", code);
            jsonObject.put("message", message);
            if (data == null){
                data = new HashMap<>();
            }
            jsonObject.put("data", new JSONObject(objectMapper.writeValueAsString(data)));
        } catch (JSONException e) {
            throw new BaseException(-1, "服务器内部错误", e);
        } catch (IOException e){
            throw new BaseException(-1, "服务器内部错误", e);
        }
        return jsonObject.toString();
    }

    public static String constructListResultJson(String code, Object message, List data) throws BaseException{
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("code", code);
            jsonObject.put("message", message);
            if (data == null){
                data = new ArrayList<>();
            }
            jsonObject.put("data", new JSONArray(new String(objectMapper.writeValueAsBytes(data), "UTF-8")));
        } catch (JSONException e) {
            throw new BaseException(-1, "服务器内部错误", e);
        } catch (IOException e){
            throw new BaseException(-1, "服务器内部错误", e);
        }
        return jsonObject.toString();
    }
}
