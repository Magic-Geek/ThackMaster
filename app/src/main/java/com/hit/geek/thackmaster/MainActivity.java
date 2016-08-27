package com.hit.geek.thackmaster;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.TextureMapView;
import com.hit.geek.thackmaster.define.PrepareData;
import com.hit.geek.thackmaster.http.AnShengApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    final static int MESSAGE_DISPEAR = 0;
    final static int GETINFOBYANSHENGAPI = 1;

    List<PrepareData> dataList = new ArrayList<>();

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case MESSAGE_DISPEAR:
                    if (message.getVisibility()!=View.VISIBLE) {
                        return;
                    }
                    handler.removeMessages(MESSAGE_DISPEAR);
                    Animation anim = new TranslateAnimation(0, 0, 0, -message.getHeight());
                    anim.setDuration(200);
                    anim.setAnimationListener(new Animation.AnimationListener() {
                        public void onAnimationStart(Animation animation) {
                        }

                        public void onAnimationRepeat(Animation animation) {
                        }

                        public void onAnimationEnd(Animation animation) {
                            message.setVisibility(View.INVISIBLE);
                        }
                    });
                    message.startAnimation(anim);
                    break;
                case GETINFOBYANSHENGAPI:
                    dataList = (List<PrepareData>) msg.obj;
                    break;
            }
        }
    };

    CardView message;
    TextureMapView mMapView;
    TextView title;
    TextView summary;
    Map map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMapView = (TextureMapView) findViewById(R.id.mapView);
        message = (CardView) findViewById(R.id.message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        map = new Map(this,mMapView,"北京","杭州");
        AnShengApi.GetKnowledge("杭州",handler);

        Button close = (Button) findViewById(R.id.close);
        title = (TextView) findViewById(R.id.title);
        summary = (TextView) findViewById(R.id.summary);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(MESSAGE_DISPEAR);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.message&&message.getVisibility()!=View.VISIBLE&&dataList.size()>0) {
            PrepareData data = dataList.get(new Random().nextInt(dataList.size()));
            summary.setText(data.summary);
            title.setText(data.title);
            Animation anim = new TranslateAnimation(0, 0, -message.getHeight(),
                    0);
            anim.setDuration(200);
            anim.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    message.setVisibility(View.VISIBLE);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            handler.sendEmptyMessage(MESSAGE_DISPEAR);
                        }
                    },5000);
                }
            });
            message.startAnimation(anim);
            return true;
        }
        if(id == R.id.hotels){
            Intent intent=new Intent(MainActivity.this,HotelsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }
}