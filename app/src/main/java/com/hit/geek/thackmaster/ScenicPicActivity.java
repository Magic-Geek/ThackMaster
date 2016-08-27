package com.hit.geek.thackmaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by eason on 8/27/16.
 */
public class ScenicPicActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenicpic);

        ImageView image = (ImageView) findViewById(R.id.pic);
        image.setImageResource(R.drawable.thackName);

        FloatingActionButton start = (FloatingActionButton) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScenicPicActivity.this,ARActivity.class);
                ScenicPicActivity.this.startActivity(intent);
            }
        });
    }
}
