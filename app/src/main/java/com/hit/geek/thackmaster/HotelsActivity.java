package com.hit.geek.thackmaster;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hit.geek.thackmaster.adapter.HotelsViewAdapter;

import java.util.*;
import java.util.Map;

public class HotelsActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        List<java.util.Map<String, Object>> data = getDate();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        HotelsViewAdapter adapter = new HotelsViewAdapter(this, data);
        recyclerView.setAdapter(adapter);

    }

    private List<java.util.Map<String, Object>> getDate() {
        List<java.util.Map<String, Object>> data = new ArrayList<java.util.Map<String, Object>>();
        Bitmap icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.hotel);
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon);
            map.put("tittle", "这是一个标题" + i);
            map.put("info", "这是一个详细信息" + i);
            data.add(map);
        }
        return data;
    }

}
