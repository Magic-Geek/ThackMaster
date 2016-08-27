package com.hit.geek.thackmaster.define;

import com.hit.geek.thackmaster.R;
import com.hit.geek.thackmaster.action.Action;
import com.hit.geek.thackmaster.action.DemoAction;
import com.hit.geek.thackmaster.view.DemoView;
import com.hit.geek.thackmaster.view.LineView;
import com.hit.geek.thackmaster.view.View;

/**
 * Created by eason on 8/22/16.
 */
public enum BluePrint {
    SOMETHING(DemoAction.class,DemoView.class, R.mipmap.ic_launcher,7),
    CITY(DemoAction.class,DemoView.class,R.drawable.city,7),
    HOTEL(DemoAction.class,DemoView.class,R.drawable.hotel,5),
    SCENIC(DemoAction.class,DemoView.class,R.drawable.scenic,5),
    LINE(DemoAction.class,LineView.class,R.color.primary_dark,4);

    Class action;
    Class view;
    int resource;
    int zIndex;

    BluePrint(Class<? extends Action> action,Class<? extends View> view,int resource,int zIndex){
        this.action = action;
        this.view = view;
        this.resource = resource;
        this.zIndex = zIndex;
    }

    public Action createAction() {
        try {
            return (Action)action.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public View createView() {
        try {
            return (View)view.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
