package com.hit.geek.thackmaster.view;

import com.baidu.mapapi.clusterutil.clustering.ClusterManager;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.hit.geek.thackmaster.define.MarkerBean;
import com.hit.geek.thackmaster.struct.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eason on 8/27/16.
 */
public class LineView implements View{
    Element element;
    Overlay line;
    boolean visible = true;
    @Override
    public void setElement(Element element) {
        this.element = element;
    }

    @Override
    public void onDraw(BaiduMap mBaiduMap, ClusterManager mClusterManager) {
        if(element.getData().children!=null){
            List<LatLng> points = new ArrayList<LatLng>();
            for(MarkerBean markerBean:element.getData().children){
                points.add(markerBean.point);
            }
            OverlayOptions ooPolyline = new PolylineOptions().width(15).color(element.getData().getResourceId())
                    .points(points).dottedLine(true).keepScale(true);
            line = mBaiduMap.addOverlay(ooPolyline);
            line.setZIndex(element.getData().getZIndex());
        }
    }

    @Override
    public void switchVisible(BaiduMap mBaiduMap) {
        visible = !visible;
        line.setVisible(visible);
    }

    @Override
    public boolean getVisible() {
        return visible;
    }
}
