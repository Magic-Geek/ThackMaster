package com.hit.geek.thackmaster.action;

import android.content.Context;
import android.os.Handler;

import com.hit.geek.thackmaster.struct.Element;

/**
 * Created by eason on 8/22/16.
 */
public interface Action{
    void setElement(Element element);
    void onClick(Context context,Handler handler);
}
