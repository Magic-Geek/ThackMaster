package com.hit.geek.thackmaster.define;

import com.baidu.mapapi.model.LatLng;

import java.util.List;

/**
 * Created by eason on 8/22/16.
 */
public class MarkerBean {
    public MarkerBean(String id,String type,LatLng point){
        this.id = id;
        this.point = point;
        this.type = type;
    }

    public String id;
    public LatLng point;
    public String type;
    public List<MarkerBean> children;

    public int getResourceId(){
        return BluePrint.valueOf(type).resource;
    }
}
