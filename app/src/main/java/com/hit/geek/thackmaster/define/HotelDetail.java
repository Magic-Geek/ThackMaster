package com.hit.geek.thackmaster.define;

import com.hit.geek.thackmaster.utils.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by eason on 8/27/16.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class HotelDetail {
    public String name;
    public String photo;
    public String address;
    public String phone;
    public String desc;
    public int minrate;
    public int maxrate;
}
