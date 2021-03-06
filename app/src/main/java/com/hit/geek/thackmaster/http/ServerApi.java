package com.hit.geek.thackmaster.http;

import android.os.Handler;
import android.util.Log;

import com.hit.geek.thackmaster.define.HotelDetail;
import com.hit.geek.thackmaster.define.MarkerBean;
import com.hit.geek.thackmaster.define.Trace;
import com.hit.geek.thackmaster.utils.HttpsUtils;

import org.xutils.common.Callback;

import java.util.List;

/**
 * Created by eason on 8/26/16.
 */
public class ServerApi {
    public static String host = "http://123.206.220.49";

    public static void GetTrace(final Handler handler){
        HttpsUtils.Get(host+"/api/trace", null, new Callback.CommonCallback<Trace>(){

            @Override
            public void onCancelled(CancelledException arg0) {}

            @Override
            public void onError(Throwable ex, boolean isCheck) {
                Log.i("error","json lost");
            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onSuccess(Trace result) {
                // TODO Auto-generated method stub
                handler.obtainMessage(3,result).sendToTarget();
            }

        });
    }

    public static void GetSpots(final Handler handler){
        HttpsUtils.Get(host+"/api/hotel", null, new Callback.CommonCallback<List<MarkerBean>>(){

            @Override
            public void onCancelled(CancelledException arg0) {}

            @Override
            public void onError(Throwable ex, boolean isCheck) {
                Log.i("error","json lost");
            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onSuccess(List<MarkerBean> result) {
                // TODO Auto-generated method stub
                handler.obtainMessage(2,result).sendToTarget();
            }

        });
    }

    public static void GetHotels(final Handler handler){
        HttpsUtils.Get(host+"/api/hotel2", null, new Callback.CommonCallback<List<HotelDetail>>(){

            @Override
            public void onCancelled(CancelledException arg0) {}

            @Override
            public void onError(Throwable ex, boolean isCheck) {
                Log.i("error2",ex.toString());
            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onSuccess(List<HotelDetail> result) {
                // TODO Auto-generated method stub
                handler.obtainMessage(2,result).sendToTarget();
                Log.i("onsucc","Onsuccess"+result);
            }
        });
    }

    public static  void GetHotel(String id,final Handler handler) {
        HttpsUtils.Get(host+"/api/hotel/" + id, null, new Callback.CommonCallback<HotelDetail>() {

            @Override
            public void onCancelled(Callback.CancelledException arg0) {
            }

            @Override
            public void onError(Throwable ex, boolean isCheck) {
                Log.i("error", "json lost");
            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onSuccess(HotelDetail result) {
                // TODO Auto-generated method stub
                handler.obtainMessage(2,result).sendToTarget();
            }

        });
    }
}
