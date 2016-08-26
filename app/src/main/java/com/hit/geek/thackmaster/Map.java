package com.hit.geek.thackmaster;

import android.content.Context;
import android.graphics.Color;

import com.baidu.mapapi.clusterutil.clustering.ClusterManager;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.hit.geek.thackmaster.define.MarkerBean;
import com.hit.geek.thackmaster.factory.BluePrintFactory;
import com.hit.geek.thackmaster.struct.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eason on 8/22/16.
 */

public class Map implements ClusterManager.ControlCityOverlay {
    public static final String PATH_CONFIG = "/sdcard/custom_config";
    MapView mapView;
    ClusterManager clusterManager;
    MapStatus mapStatus;
    List<MarkerBean> city = new ArrayList<>();
    List<Element> elements;
    Overlay line;
    Context context;

    public Map(Context context, final MapView mapView, final String start, final String end){
        this.mapView = mapView;
        this.context = context;

        clusterManager = new ClusterManager(context,mapView.getMap());
        clusterManager.setControlCityOverlayInterface(this);

        final GeoCoder mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            public void onGetGeoCodeResult(GeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    return;
                }
                //获取地理编码结果
                LatLng point = result.getLocation();
                MarkerBean marker = new MarkerBean(result.getAddress(),"CITY",point);
                city.add(marker);
                if(city.size() == 2){
                    draw(city);
                    mapStatus = new MapStatus.Builder().target(point).zoom(6).build();
                    mapView.getMap().animateMapStatus(MapStatusUpdateFactory.newMapStatus(mapStatus));

                    mSearch.destroy();
                }else{
                    mSearch.geocode(new GeoCodeOption().city(end).address(end));
                }
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    return;
                }
            }
        });

        mSearch.geocode(new GeoCodeOption().city(start).address(start));

        initConfig();
        initListener();
    }

    public void draw(List<MarkerBean> data){
        if(elements==null) elements = new ArrayList<>();
        List<LatLng> points = new ArrayList<LatLng>();
        List<Integer> colors = new ArrayList<>();
        for(MarkerBean bean : data){
            final Element element = BluePrintFactory.createElement(mapView,bean);
            element.getView().onDraw(mapView.getMap(),clusterManager);
            elements.add(element);
            points.add(bean.point);
        }
        colors.add(Integer.valueOf(Color.RED));
        OverlayOptions ooPolyline = new PolylineOptions().width(10)
                .colorsValues(colors).points(points);
        line = mapView.getMap().addOverlay(ooPolyline);
    }

    private void initConfig(){
        MapView.setCustomMapStylePath(PATH_CONFIG);
        MapView.setMapCustomEnable(true);
        mapView.showZoomControls(false);
        mapView.getMap().showMapPoi(false);
        mapView.getMap().setTrafficEnabled(true);
    }

    private void initListener(){
        final BaiduMap map = mapView.getMap();
        map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String name = marker.getTitle();
                for(Element m:elements){
                    if(m.getData().id.equals(name)){
                        m.getAction().onClick();
                        break;
                    }
                }
                return false;
            }
        });

        map.setOnMapStatusChangeListener(clusterManager);
        map.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mapStatus = new MapStatus.Builder().zoom(6).build();
                map.animateMapStatus(MapStatusUpdateFactory.newMapStatus(mapStatus));
            }
        });
    }

    public static void writeConfigFile(Context context){
        InputStream input = null;
        FileOutputStream fos = null;
        try {
            File file = new File(PATH_CONFIG);
            if(!file.exists()){
                file.createNewFile();
            }
            input = context.getAssets().open("custom_config");
            fos = new FileOutputStream(file);
            int ch = 0;
            while((ch=input.read())!=-1){
                fos.write(ch);
            }
            fos.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean isCityShow = true;

    @Override
    public void hideCity() {
        if(isCityShow){
            isCityShow = false;
            for(Element ele : elements){
                ele.getView().switchVisible(mapView.getMap());
            }
            line.setVisible(isCityShow);
        }
    }

    @Override
    public void showCity() {
        if(!isCityShow){
            isCityShow = true;
            for(Element ele : elements){
                ele.getView().switchVisible(mapView.getMap());
            }
            line.setVisible(isCityShow);
        }
    }
}
