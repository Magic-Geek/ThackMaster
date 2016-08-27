package com.hit.geek.thackmaster;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.hit.geek.thackmaster.adapter.ItemPagerAdapter;
import com.hit.geek.thackmaster.bottomsheet.BottomSheetBehaviorGoogleMapsLike;
import com.hit.geek.thackmaster.bottomsheet.RoadItemAdapter;
import com.hit.geek.thackmaster.define.MarkerBean;
import com.hit.geek.thackmaster.define.PrepareData;
import com.hit.geek.thackmaster.define.Trace;
import com.hit.geek.thackmaster.define.Road;
import com.hit.geek.thackmaster.http.AnShengApi;
import com.hit.geek.thackmaster.http.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int[] sightPictures = {
            R.drawable.cheese_3,
            R.drawable.cheese_3,
            R.drawable.cheese_3,
            R.drawable.cheese_3,
            R.drawable.cheese_3,
            R.drawable.cheese_3
    };

    private TextView tripGoalText;
    private TextView tripDistanceText;
    private TextView tripTimeText;

    RecyclerView recyclerview;

    final static int MESSAGE_DISPEAR = 0;
    final static int GETINFOBYANSHENGAPI = 1;
    final static int GETINFOOFALLSPOTS = 2;
    final static int GETTRACE = 3;
    final static int STARTSCENIVPICACT = 9;
    final static int STARTHOTELACT = 10;

    List<PrepareData> dataList = new ArrayList<>();
    List<MarkerBean> markers = new ArrayList<>();
    List<MarkerBean> lines = new ArrayList<>();
    Trace trace = new Trace();

    List<Road> items;

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
                    Intent intent = new Intent(MainActivity.this, ScenicPicActivity.class);
                    intent.putExtra("id",(String)msg.obj);
                    startActivity(intent);
                    break;
                case GETTRACE:
                    trace = (Trace) msg.obj;
                    List<MarkerBean> lines = new ArrayList<>();
                    for(int i=0;i<trace.economyArray.length();i++){
                        JSONObject o = trace.economy(i);
                        List<MarkerBean> points = new ArrayList<>();
                        if(o.has("path")){
                            try {
                                JSONArray array = o.getJSONArray("path");
                                for(int j=0;j<array.length();j++){
                                    JSONObject oo = array.getJSONObject(j);
                                    LatLng point = new LatLng(
                                            oo.getDouble("latitude"),
                                            oo.getDouble("longitude")
                                    );
                                    MarkerBean markerBean = new MarkerBean("cline"+j,"SOMETHING",point);
                                    points.add(markerBean);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            MarkerBean markerBean = new MarkerBean("fLINE"+i,"LINE",null);
                            markerBean.children = points;
                            lines.add(markerBean);
                        }
                    }
                    map.draw(lines);
                    break;
                case STARTHOTELACT:
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
        ServerApi.GetTrace(handler);


        Button close = (Button) findViewById(R.id.close);
        title = (TextView) findViewById(R.id.title);
        summary = (TextView) findViewById(R.id.summary);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(MESSAGE_DISPEAR);
            }
        });

        /**
         * If we want to listen for states callback
         */
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorlayout);
        View bottomSheet = coordinatorLayout.findViewById(R.id.bottom_sheet);
        final BottomSheetBehaviorGoogleMapsLike behavior = BottomSheetBehaviorGoogleMapsLike.from(bottomSheet);
        behavior.addBottomSheetCallback(new BottomSheetBehaviorGoogleMapsLike.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED:
                        Log.d("bottomsheet-", "STATE_COLLAPSED");
                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_DRAGGING:
                        Log.d("bottomsheet-", "STATE_DRAGGING");
                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_EXPANDED:
                        Log.d("bottomsheet-", "STATE_EXPANDED");
                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_ANCHOR_POINT:
                        Log.d("bottomsheet-", "STATE_ANCHOR_POINT");
                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_HIDDEN:
                        Log.d("bottomsheet-", "STATE_HIDDEN");
                        break;
                    default:
                        Log.d("bottomsheet-", "STATE_SETTLING");
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

//        AppBarLayout mergedAppBarLayout = (AppBarLayout) findViewById(R.id.merged_appbarlayout);
//        MergedAppBarLayoutBehavior mergedAppBarLayoutBehavior = MergedAppBarLayoutBehavior.from(mergedAppBarLayout);
//        mergedAppBarLayoutBehavior.setToolbarTitle("选择出行线路");
//        mergedAppBarLayoutBehavior.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                behavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED);
//            }
//        });

        tripGoalText = (TextView) bottomSheet.findViewById(R.id.tripgoal_textview);
        tripDistanceText = (TextView)bottomSheet.findViewById(R.id.tripdistance_textview);
        tripTimeText = (TextView)bottomSheet.findViewById(R.id.triptime_textview);

        ItemPagerAdapter sightAdapter = new ItemPagerAdapter(this,sightPictures);
        ViewPager viewPager = (ViewPager) findViewById(R.id.sight_viewpager);
        viewPager.setAdapter(sightAdapter);

        recyclerview = (RecyclerView)findViewById(R.id.road_recyclerview);
        RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setHasFixedSize(true);
        recyclerview.setItemAnimator(new DefaultItemAnimator());

        items = new ArrayList<>();
        addRoadItems();
        final RoadItemAdapter myadapter = new RoadItemAdapter(items);
        recyclerview.setAdapter(myadapter);

    }

    public void addRoadItems(){

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