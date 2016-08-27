package com.hit.geek.thackmaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.hit.geek.thackmaster.adapter.IntroAdapter;
import java.util.ArrayList;

/**
 * Created by eason on 8/27/16.
 */

public class IntroActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    IntroAdapter adapter;
    ArrayList<String> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Map.writeConfigFile(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        int spanCount = 2;
        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(
                spanCount,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new IntroAdapter();
        recyclerView.setAdapter(adapter);

        FloatingActionButton travel = (FloatingActionButton) findViewById(R.id.travel);
        travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this,SearchActivity.class);
                IntroActivity.this.startActivity(intent);
            }
        });
    }
}
