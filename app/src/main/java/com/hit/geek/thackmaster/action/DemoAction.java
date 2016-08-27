package com.hit.geek.thackmaster.action;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.baidu.mapapi.model.LatLng;
import com.hit.geek.thackmaster.struct.Element;

/**
 * Created by eason on 8/22/16.
 */
public class DemoAction implements Action{
    @Override
    public void setElement(Element element) {

    }

    @Override
    public void onClick(Context context, Handler handler) {
        Log.d("demo action","it is clicked");
    }
}
