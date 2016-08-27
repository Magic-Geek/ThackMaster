package com.hit.geek.thackmaster.define;

/**
 * Created by dusz2 on 2016/8/27 0027.
 */
public class Road {
    private String departPlace;
    private String arrivePlace;
    private String time;
    private String transprotType;

    public Road(){

    }

    public Road(String departPlace, String arrivePlace, String time, String transprotType) {
        this.departPlace = departPlace;
        this.arrivePlace = arrivePlace;
        this.time = time;
        this.transprotType = transprotType;
    }

    public String getDepartPlace() {
        return departPlace;
    }

    public void setDepartPlace(String departPlace) {
        this.departPlace = departPlace;
    }

    public String getArrivePlace() {
        return arrivePlace;
    }

    public void setArrivePlace(String arrivePlace) {
        this.arrivePlace = arrivePlace;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTransprotType() {
        return transprotType;
    }

    public void setTransprotType(String transprotType) {
        this.transprotType = transprotType;
    }


}
