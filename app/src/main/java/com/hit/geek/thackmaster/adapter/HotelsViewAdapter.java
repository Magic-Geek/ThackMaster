package com.hit.geek.thackmaster.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hit.geek.thackmaster.HotelInfoActivity;
import com.hit.geek.thackmaster.HotelsActivity;
import com.hit.geek.thackmaster.R;
import com.hit.geek.thackmaster.define.HotelDetail;
import com.hit.geek.thackmaster.define.PhotoData;

import org.xutils.x;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by daofa on 2016/8/27.
 */
public class HotelsViewAdapter extends RecyclerView.Adapter<HotelsViewAdapter.ViewHolder>{

    private int current = 0;
    private List<HotelDetail> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public HotelsViewAdapter(Context context, List<HotelDetail> data){
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,HotelInfoActivity.class);
                intent.putExtra("id",data.get(position).id);
                context.startActivity(intent);
            }
        });
        current = position;
        PhotoData photoData=data.get(position).photo.get(0);
        x.image().bind(holder.image,photoData.url_original);
        Log.i("photo","url is : "+data.get(position).phone);
        holder.name.setText(data.get(position).name);
        holder.address.setText(data.get(position).address);
        holder.phone.setText(data.get(position).phone);
        holder.desc.setText(data.get(position).desc);
        holder.price.setText(data.get(position).maxrate+"---"+data.get(position).minrate);
    }

    public HotelDetail getDetailData(){
        return data.get(current);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public ImageView image;
        public TextView name;
        public TextView address;
        public TextView phone;
        public TextView desc;
        public TextView price;

        public ViewHolder(View itemView){
            super(itemView);
            cardView=(CardView)itemView.findViewById(R.id.cv_item);
            image = (ImageView)itemView.findViewById(R.id.hotel_image);
            name = (TextView) itemView.findViewById(R.id.hotel_name);
            address = (TextView) itemView.findViewById(R.id.hotel_address);
            phone = (TextView) itemView.findViewById(R.id.hotel_phone);
            desc = (TextView) itemView.findViewById(R.id.hotel_desc);
            price = (TextView) itemView.findViewById(R.id.hotel_price);
        }
    }
}
