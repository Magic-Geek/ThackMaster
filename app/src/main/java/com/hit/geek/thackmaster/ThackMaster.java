package com.hit.geek.thackmaster;

import android.app.Application;

import org.xutils.x;

/**
 * Created by eason on 8/26/16.
 */
public class ThackMaster extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}