package com.hit.geek.thackmaster;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.hit.geek.thackmaster.action.HotelAction;
import com.hit.geek.thackmaster.adapter.HotelsViewAdapter;
import com.hit.geek.thackmaster.define.HotelDetail;
import com.hit.geek.thackmaster.http.ServerApi;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class HotelInfoActivity extends AppCompatActivity {

    private String id;
    private HotelDetail hotelDetal;

    private ImageView image;
    private TextView name;
    private TextView address;
    private TextView phone;
    private TextView describe;
    private TextView price;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==2){
                if(msg.obj!=null){
                    hotelDetal=(HotelDetail)(msg.obj);
                    setCurrentData(hotelDetal);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_info);
        id=getIntent().getStringExtra("id");

        image = (ImageView)findViewById(R.id.hotel_info_image);
        name = (TextView)findViewById(R.id.hotel_info_name);
        address=(TextView)findViewById(R.id.hotel_info_address);
        phone=(TextView)findViewById(R.id.hotel_info_phone);
        price=(TextView)findViewById(R.id.hotel_info_price);
        describe=(TextView)findViewById(R.id.hotel_info_describe);

        ServerApi.GetHotel(id,handler);
    }

    public void setCurrentData(HotelDetail detail){
        Log.i("setcurr",""+detail.name);
        String url=(detail.photo).get(0).url_original;
        Log.i("uuu","uuu ; "+url);
        x.image().bind(image,url);
        name.setText("酒店名称 ： "+detail.name);
        address.setText("地址 ： "+detail.address);
        phone.setText("电话 ： "+detail.phone);
        price.setText("价格区间 ： "+detail.minrate+" 到 "+detail.maxrate);
        describe.setText("详情 ： "+detail.desc);

    }
}
