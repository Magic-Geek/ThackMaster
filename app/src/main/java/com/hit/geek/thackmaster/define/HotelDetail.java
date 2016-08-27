package com.hit.geek.thackmaster.define;

import com.hit.geek.thackmaster.utils.JsonResponseParser;

import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by eason on 8/27/16.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class HotelDetail implements Serializable{
    private static final long serialVersionUID = -7060210544600464481L;
    public String name;
    public List<PhotoData> photo;
    public String address;
    public String phone;
    public String desc;
    public float minrate;
    public float maxrate;

    public HotelDetail(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PhotoData> getPhoto() {
        return photo;
    }

    public void setPhoto(List<PhotoData> photo) {
        this.photo = photo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public float getMinrate() {
        return minrate;
    }

    public void setMinrate(float minrate) {
        this.minrate = minrate;
    }

    public float getMaxrate() {
        return maxrate;
    }

    public void setMaxrate(float maxrate) {
        this.maxrate = maxrate;
    }

}
