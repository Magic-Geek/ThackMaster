package com.hit.geek.thackmaster;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hit.geek.thackmaster.adapter.HotelsViewAdapter;
import com.hit.geek.thackmaster.define.HotelDetail;
import com.hit.geek.thackmaster.http.ServerApi;

import java.util.*;
import java.util.Map;

public class HotelsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<HotelDetail> hotelDetails=new ArrayList<HotelDetail>();
    public HotelsViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
//        hotelDetails = getDate();

//        final View.OnClickListener listener=new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HotelsActivity.this,HotelInfoActivity.class);
//                Bundle mBundle = new Bundle();
//                mBundle.putSerializable("hotel",adapter.getDetailData() );
//                intent.putExtras(mBundle);
//                startActivity(intent);
//            }
//        };
        Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==2){
                    if(msg.obj!=null){
                        hotelDetails=(List<HotelDetail>)(msg.obj);
                        Log.i("detail","ddd : "+msg.obj);

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HotelsActivity.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());

                        adapter = new HotelsViewAdapter(HotelsActivity.this, hotelDetails);
                        recyclerView.setAdapter(adapter);

                    }
                }
            }
        };
        ServerApi.GetHotels(handler);

//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//
//        HotelsViewAdapter adapter = new HotelsViewAdapter(this, hotelDetails);
//        recyclerView.setAdapter(adapter);

    }

    List<HotelDetail> data=new ArrayList<HotelDetail>();
    private List<HotelDetail> getDate() {

        Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==2){
                    if(msg.obj!=null){
                        data=(List<HotelDetail>)(msg.obj);
                        Log.i("detail","ddd : "+msg.obj);
                    }
                }
            }
        };
        ServerApi.GetHotels(handler);

        return data;
    }

}
