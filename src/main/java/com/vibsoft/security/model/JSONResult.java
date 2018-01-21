package com.vibsoft.security.model;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONResult{
    public static String fillResultString(Integer status, String message, Object result){
        JSONObject jsonObject = new JSONObject(){{
            try {
                put("status", status);
                put("message", message);
                put("result", result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }};

        return jsonObject.toString();
    }
}
