package com.hit.geek.thackmaster.adapter;

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
    private List<String[]> entry = new ArrayList<>();
    private enum array{美食,风景,宾馆,美女,名人,名企}
    private int[] colorIndex = new int[]{R.color.color1,R.color.color2,R.color.color3,R.color.color4,R.color.color5,R.color.color6};
    public IntroAdapter() {
        entry.add(new String[]{"名企","http://img1.imgtn.bdimg.com/it/u=3091277483,1191516122&fm=206&gp=0.jpg"});
        entry.add(new String[]{"美食","http://img0.imgtn.bdimg.com/it/u=3990401445,626862199&fm=21&gp=0.jpg"});
        entry.add(new String[]{"风景","http://img5.imgtn.bdimg.com/it/u=1794869289,1437274802&fm=21&gp=0.jpg"});
        entry.add(new String[]{"美食","http://img1.imgtn.bdimg.com/it/u=4195614983,4145708316&fm=21&gp=0.jpg"});
        entry.add(new String[]{"风景","http://img5.imgtn.bdimg.com/it/u=2272813393,1661974132&fm=206&gp=0.jpg"});
        entry.add(new String[]{"宾馆","http://images.sunnychina.com/hotel_images/12/20130321113812p0O7TGt8.jpg"});
        entry.add(new String[]{"美女","http://img0.imgtn.bdimg.com/it/u=1821788731,3392964255&fm=206&gp=0.jpg"});
        entry.add(new String[]{"名人","http://img4.imgtn.bdimg.com/it/u=4234511868,720638546&fm=206&gp=0.jpg"});
        entry.add(new String[]{"美食","http://img1.imgtn.bdimg.com/it/u=4195614983,4145708316&fm=21&gp=0.jpg"});
        entry.add(new String[]{"名企","http://p1.img.cctvpic.com/photoworkspace/contentimg/2016/04/28/2016042816362345841.jpg"});
        entry.add(new String[]{"美食","http://img0.imgtn.bdimg.com/it/u=3990401445,626862199&fm=21&gp=0.jpg"});
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
        String[] values = entry.get(position);
        View view = viewHolder.itemView;
        TextView textView = (TextView) view.findViewById(R.id.info_text);
        ImageView imageView = (ImageView) view.findViewById(R.id.info_image);
        LinearLayout container = (LinearLayout) view.findViewById(R.id.container);
        textView.setText(values[0]);
        int color = colorIndex[array.valueOf(values[0]).ordinal()];
        container.setBackgroundResource(color);
        x.image().bind(imageView,values[1]);
    }

    @Override
    public int getItemCount() {
        return entry.size()-1;
    }

    class viewHolder extends RecyclerView.ViewHolder {
        public viewHolder(View itemView) {
            super(itemView);
        }
    }
}