package com.hit.geek.thackmaster.adapter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hit.geek.thackmaster.R;

import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by eason on 8/27/16.
 */
public class IntroAdapter extends RecyclerView.Adapter<IntroAdapter.viewHolder> {
    private int[] image = new int[]{
            R.mipmap.a1,
            R.mipmap.a2,
            R.mipmap.a3,
            R.mipmap.a4,
            R.mipmap.a5,
            R.mipmap.a6,
            R.mipmap.a7,
            R.mipmap.a8,
            R.mipmap.a9,
            R.mipmap.a10,
            R.mipmap.a11,
            R.mipmap.a12,
            R.mipmap.a13,
            R.mipmap.a14,
    };
    private int[] colorIndex = new int[]{R.color.color1,R.color.color2,R.color.color3,R.color.color4,R.color.color5,R.color.color6};
    public IntroAdapter() {
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.intro_item,
                viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(viewHolder viewHolder, int position) {
        View view = viewHolder.itemView;
        TextView textView = (TextView) view.findViewById(R.id.info_text);
        ImageView imageView = (ImageView) view.findViewById(R.id.info_image);
        LinearLayout container = (LinearLayout) view.findViewById(R.id.container);
        textView.setText("简 介");
        int color = colorIndex[position%6];
        container.setBackgroundResource(color);
        imageView.setImageResource(image[position]);
    }

    @Override
    public int getItemCount() {
        return image.length-1;
    }

    class viewHolder extends RecyclerView.ViewHolder {
        public viewHolder(View itemView) {
            super(itemView);
        }
    }
}