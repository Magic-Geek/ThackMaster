package com.hit.geek.thackmaster.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hit.geek.thackmaster.R;

import java.util.List;
import java.util.Map;

/**
 * Created by daofa on 2016/8/27.
 */
public class HotelsViewAdapter extends RecyclerView.Adapter<HotelsViewAdapter.ViewHolder> {


    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public HotelsViewAdapter(Context context, List<Map<String, Object>> data){
        this.context=context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=layoutInflater.inflate(R.layout.hotel_item,null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.image.setImageBitmap((Bitmap)(data.get(position).get("image")));
        holder.tittle.setText((String)(data.get(position).get("tittle")));
        holder.info.setText((String)(data.get(position).get("info")));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView tittle;
        public TextView info;
        public ViewHolder(View itemView){
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.hotel_image);
            tittle = (TextView) itemView.findViewById(R.id.hotel_tittle);
            info = (TextView) itemView.findViewById(R.id.hotel_info);
        }
    }
}
