package com.hit.geek.thackmaster.define;

import com.hit.geek.thackmaster.utils.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by eason on 8/26/16.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class AnShengJson {
    public String errcode;
    public String errmsg;
    public String guid;
    public PrepareDataJson data;
}
