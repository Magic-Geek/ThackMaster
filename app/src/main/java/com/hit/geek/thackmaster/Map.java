package com.hit.geek.thackmaster;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.clusterutil.clustering.ClusterManager;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextureMapView;
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

import org.xutils.x;

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
    TextureMapView mapView;
    ClusterManager clusterManager;
    MapStatus mapStatus;
    LocationClient mLocationClient;
    BDLocationListener myListener;
    List<MarkerBean> city = new ArrayList<>();
    List<Element> elements;
    Overlay person;
    Context context;

    public Map(Context context, final TextureMapView mapView, final String start, final String end){
        this.mapView = mapView;
        this.context = context;

        clusterManager = new ClusterManager(context,mapView.getMap());
        clusterManager.setControlCityOverlayInterface(this);

        mLocationClient = new LocationClient(x.app());

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
                    MarkerBean line = new MarkerBean("line0","LINE",null);
                    for(MarkerBean c : city){
                        line.children.add(c);
                    }
                    city.add(line);
                    draw(city);
                    mapStatus = new MapStatus.Builder().target(point).zoom(6).build();
                    mapView.getMap().animateMapStatus(MapStatusUpdateFactory.newMapStatus(mapStatus));
                    mLocationClient.start();
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
        for(MarkerBean bean : data){
            final Element element = BluePrintFactory.createElement(bean);
            element.getView().onDraw(mapView.getMap(),clusterManager);
            elements.add(element);
            points.add(bean.point);
        }
    }

    private void initConfig(){
        MapView.setCustomMapStylePath(PATH_CONFIG);
        MapView.setMapCustomEnable(true);
        mapView.showZoomControls(false);
        mapView.getMap().showMapPoi(false);
        mapView.getMap().setTrafficEnabled(true);
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    private void initListener(){
        final BaiduMap map = mapView.getMap();
        map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle data = marker.getExtraInfo();
                String id = data.getString("id");

                for(Element m:elements){
                    if(m.getData().id.equals(id)){
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

        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                LatLng point = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
                if(person != null){
                    person.remove();
                }
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.go);
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bitmap).title("person");
                person = mapView.getMap().addOverlay(option);
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
                if(ele.getData().type.equals("CITY")||ele.getData().id.equals("line0"))
                    ele.getView().switchVisible(mapView.getMap());
            }
        }
    }

    @Override
    public void showCity() {
        if(!isCityShow){
            isCityShow = true;
            for(Element ele : elements){
                if(ele.getData().type.equals("CITY")||ele.getData().id.equals("line0"))
                    ele.getView().switchVisible(mapView.getMap());
            }
        }
    }
}
