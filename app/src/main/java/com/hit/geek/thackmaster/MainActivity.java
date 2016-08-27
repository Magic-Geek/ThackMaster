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
import com.hit.geek.thackmaster.action.ScenicAction;
import com.hit.geek.thackmaster.define.MarkerBean;
import com.hit.geek.thackmaster.define.PrepareData;
import com.hit.geek.thackmaster.http.AnShengApi;
import com.hit.geek.thackmaster.http.ServerApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    final static int MESSAGE_DISPEAR = 0;
    final static int GETINFOBYANSHENGAPI = 1;
    final static int GETINFOOFALLSPOTS = 2;
    final static int STARTSCENIVPICACT = 9;

    List<PrepareData> dataList = new ArrayList<>();
    List<MarkerBean> markers = new ArrayList<>();

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
                case GETINFOOFALLSPOTS:
                    markers = (List<MarkerBean>) msg.obj;
                    map.draw(markers);
                    break;
                case STARTSCENIVPICACT:
                    Intent intent = new Intent(MainActivity.this, ARActivity.class);
                    intent.putExtra("id",(String)msg.obj);
                    startActivity(intent);
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

        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        mMapView = (TextureMapView) findViewById(R.id.mapView);
        message = (CardView) findViewById(R.id.message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String from = intent.getStringExtra("from");
        String to = intent.getStringExtra("to");
        String time = intent.getStringExtra("time");

        map = new Map(this,handler,mMapView,from,to);
        AnShengApi.GetKnowledge(to,handler);
        ServerApi.GetSpots(handler);

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