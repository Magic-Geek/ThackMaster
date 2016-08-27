package com.hit.geek.thackmaster.http;

import android.os.Handler;
import android.util.Log;

import com.hit.geek.thackmaster.define.AnShengJson;
import com.hit.geek.thackmaster.utils.HttpsUtils;
import com.hit.geek.thackmaster.utils.MD5;

import org.xutils.common.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eason on 8/26/16.
 */
public class AnShengApi {
    public static String host = "http://test.portal.axadirect.com.cn/campus/Home/Index";

    public static void GetKnowledge(String keywords, final Handler handler){
        long timeStamp = System.currentTimeMillis();
        String pid = "1000109010703273872";
        String secret = "";

        Map<String,String> map=new HashMap<>();
        map.put("keywords", keywords);
        map.put("pid",pid);
        map.put("timestamp",timeStamp+"");
        map.put("sign", MD5.MD5(pid+secret+timeStamp));

        HttpsUtils.Get(host+"/queryHandle", map, new Callback.CommonCallback<AnShengJson>(){

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
            public void onSuccess(AnShengJson result) {
                // TODO Auto-generated method stub
                handler.obtainMessage(1,result.data.list).sendToTarget();
            }

        });
    }
}
