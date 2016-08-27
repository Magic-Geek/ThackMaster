package com.hit.geek.thackmaster.action;

import android.content.Context;
import android.os.Handler;

import com.hit.geek.thackmaster.struct.Element;

/**
 * Created by eason on 8/27/16.
 */
public class ScenicAction implements Action{
    Element element;
    @Override
    public void setElement(Element element) {
        this.element = element;
    }

    @Override
    public void onClick(Context context,Handler handler) {
        handler.obtainMessage(9,element.getData().id);
        handler.sendEmptyMessage(9);
    }
}
