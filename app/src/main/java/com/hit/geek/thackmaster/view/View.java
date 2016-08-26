package com.hit.geek.thackmaster.view;

import com.baidu.mapapi.clusterutil.clustering.ClusterManager;
import com.baidu.mapapi.map.BaiduMap;
import com.hit.geek.thackmaster.struct.Element;

/**
 * Created by eason on 8/22/16.
 */
public interface View {
    void setElement(Element element);
    void onDraw(BaiduMap mBaiduMap, ClusterManager mClusterManager);
    void switchVisible(BaiduMap mBaiduMap);
    boolean getVisible();
}
