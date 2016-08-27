package com.hit.geek.thackmaster.view;

import com.baidu.mapapi.clusterutil.clustering.ClusterManager;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.hit.geek.thackmaster.define.MarkerBean;
import com.hit.geek.thackmaster.struct.Element;
import com.hit.geek.thackmaster.struct.MarkerChild;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eason on 8/22/16.
 */
public class DemoView implements View{
    Element element;
    Overlay text;
    Overlay icon;
    boolean visible = true;
    public void setElement(Element element){
        this.element = element;
    }
    @Override
    public void onDraw(BaiduMap mBaiduMap,ClusterManager mClusterManager) {
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(element.getData().getResourceId());
        OverlayOptions option = new MarkerOptions()
                .position(element.getData().point)
                .icon(bitmap).title(element.getData().id);
        icon = mBaiduMap.addOverlay(option);
        icon.setZIndex(element.getData().getZIndex());
        icon.setExtraInfo(element.getData().genExtraInfo());
        OverlayOptions textOption = new TextOptions()
                .bgColor(0xAAFFFF00)
                .fontSize(24)
                .fontColor(0xFFFF00FF)
                .text(element.getData().name)
                .rotate(0)
                .position(element.getData().point);
        text = mBaiduMap.addOverlay(textOption);
        text.setZIndex(element.getData().getZIndex());
        if(element.getData().children!=null&&element.getData().children.size()!=0){
            List<MarkerChild> markerChildren = new ArrayList<MarkerChild>();
            for(MarkerBean child : element.getData().children){
                MarkerChild markerChild = new MarkerChild(child);
                markerChildren.add(markerChild);
            }
            markerChildren.add(new MarkerChild(element.getData()));
            mClusterManager.addItems(markerChildren);
        }
    }

    @Override
    public void switchVisible(BaiduMap mBaiduMap) {
        visible = !visible;
        text.setVisible(visible);
        icon.setVisible(visible);
    }

    @Override
    public boolean getVisible() {
        return visible;
    }
}
