package com.hit.geek.thackmaster.utils;

import com.google.gson.Gson;
import com.hit.geek.thackmaster.define.Trace;

import org.json.JSONObject;
import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.Type;

/**
 * Created by eason on 8/27/16.
 */
public class TraceParser implements ResponseParser {

    @Override
    public void checkResponse(UriRequest request) throws Throwable {
        // TODO Auto-generated method stub
    }

    @Override
    public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
        // TODO Auto-generated method stub
        if (resultClass == Trace.class) {
            JSONObject o = new JSONObject(result);
            Trace trace = new Trace();
            trace.economyArray = o.getJSONArray("economy");
            trace.fastArray = o.getJSONArray("fast");
            return trace;
        }
        return null;
    }

}
