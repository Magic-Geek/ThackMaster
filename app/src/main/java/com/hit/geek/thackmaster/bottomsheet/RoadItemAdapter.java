package com.hit.geek.thackmaster.bottomsheet;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hit.geek.thackmaster.R;
import com.hit.geek.thackmaster.define.Road;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dusz2 on 2016/8/27 0027.
 */
public class RoadItemAdapter extends RecyclerView.Adapter<RoadItemAdapter.RoadViewHolder> {


    private List<Road> items;

    public RoadItemAdapter(@NonNull List<Road> dateItems) {
        this.items = (dateItems != null ? dateItems : new ArrayList<Road>());
    }

    @Override public RoadViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.road_item, viewGroup, false);
        return new RoadViewHolder(itemView);
    }

    @Override public void onBindViewHolder(RoadViewHolder viewHolder, int position) {
//        viewHolder.textView.setText(items.get(position));
        Road item = items.get(position);
        viewHolder.dTextView.setText(item.getDepartPlace());
        viewHolder.aTextView.setText(item.getArrivePlace());
        viewHolder.typeTextView.setText(item.getTransprotType());
        viewHolder.timeTextView.setText(item.getTime());
    }

    @Override public int getItemCount() {
        return (this.items != null) ? this.items.size() : 0;
    }

    protected final static class RoadViewHolder extends RecyclerView.ViewHolder {

        protected TextView dTextView;
        protected TextView aTextView;
        protected TextView typeTextView;
        protected TextView timeTextView;

        public RoadViewHolder(View itemView) {
            super(itemView);

            dTextView = (TextView)itemView.findViewById(R.id.d_textview);
            aTextView = (TextView)itemView.findViewById(R.id.a_textview);
            typeTextView = (TextView)itemView.findViewById(R.id.type_textview);
            timeTextView = (TextView)itemView.findViewById(R.id.time_edittext);


        }
    }
}
