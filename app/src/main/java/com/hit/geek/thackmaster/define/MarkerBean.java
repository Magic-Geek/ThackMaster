package com.hit.geek.thackmaster.define;

import android.os.Bundle;

import com.baidu.mapapi.model.LatLng;
import com.hit.geek.thackmaster.R;
import com.hit.geek.thackmaster.utils.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eason on 8/22/16.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class MarkerBean {
    public MarkerBean(String id,String type,LatLng point){
        this.id = id;
        this.name = id;
        this.point = point;
        this.type = type;
    }

    public String id;
    public LatLng point;
    public String type;
    public int advise;
    public String name;
    public List<MarkerBean> children = new ArrayList<>();

    public Bundle genExtraInfo(){
        Bundle mBundle = new Bundle();
        mBundle.putString("id",id);
        mBundle.putString("type",type);
        mBundle.putInt("advice",advise);
        return mBundle;
    }

    public int getResourceId(){
        int id = BluePrint.valueOf(type).resource;

        if(type.equals("HOTEL")&&advise==1) id = R.drawable.advise;
        return id;
    }

    public int getZIndex(){
        return BluePrint.valueOf(type).zIndex;
    }
}
