package com.hit.geek.thackmaster.define;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eason on 8/27/16.
 */
public class Trace {
    public String economy;
    public String fast;
    JSONArray economyArray;
    JSONArray fastArray;

    public JSONObject economy(int i){
        JSONObject object = null;
        try {
            if(economyArray == null){
                economyArray = new JSONArray(economy);
            }

            if(i>=economyArray.length()){
                return null;
            }
            object = economyArray.getJSONObject(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject fast(int i){
        JSONObject object = null;
        try {
            if(fastArray == null){
                fastArray = new JSONArray(economy);
            }

            if(i>=fastArray.length()){
                return null;
            }
            object = fastArray.getJSONObject(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
