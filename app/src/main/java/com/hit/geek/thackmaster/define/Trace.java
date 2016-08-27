package com.hit.geek.thackmaster.define;

import com.hit.geek.thackmaster.utils.TraceParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.annotation.HttpResponse;

/**
 * Created by eason on 8/27/16.
 */
@HttpResponse(parser = TraceParser.class)
public class Trace {
    public JSONArray economyArray;
    public JSONArray fastArray;

    public JSONObject economy(int i){
        JSONObject object = null;
        try {
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
