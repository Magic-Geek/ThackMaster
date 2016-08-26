package com.hit.geek.thackmaster.factory;

import com.baidu.mapapi.map.MapView;
import com.hit.geek.thackmaster.struct.Element;
import com.hit.geek.thackmaster.define.BluePrint;
import com.hit.geek.thackmaster.define.MarkerBean;

/**
 * Created by eason on 8/22/16.
 */
public class BluePrintFactory {
    public static Element createElement(MapView mapView, MarkerBean bean){
        BluePrint bp = BluePrint.valueOf(bean.type);
        Element element = new Element(bp.createAction(),bp.createView(),bean);
        return element;
    }
}
