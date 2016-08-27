package com.hit.geek.thackmaster.http;

import android.os.Handler;
import android.util.Log;

import com.hit.geek.thackmaster.define.HotelDetail;
import com.hit.geek.thackmaster.define.MarkerBean;
import com.hit.geek.thackmaster.utils.HttpsUtils;

import org.xutils.common.Callback;

import java.util.List;

/**
 * Created by eason on 8/26/16.
 */
public class ServerApi {
    public static String host = "http://123.206.220.49:8080";

    public static void GetSpots(){
        HttpsUtils.Get("/api/hotel", null, new Callback.CommonCallback<MarkerBean>(){

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
            public void onSuccess(MarkerBean result) {
                // TODO Auto-generated method stub

            }

        });
    }

    public static void GetHotels(final Handler handler){
        HttpsUtils.Get(host+"/api/hotel2", null, new Callback.CommonCallback<List<HotelDetail>>(){

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
            public void onSuccess(List<HotelDetail> result) {
                // TODO Auto-generated method stub
                handler.obtainMessage(2,result).sendToTarget();
            }

        });
    }

    public static  void GetHotel(String id) {
        HttpsUtils.Get("/api/hotel/" + id, null, new Callback.CommonCallback<HotelDetail>() {

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

            }

        });
    }
}
