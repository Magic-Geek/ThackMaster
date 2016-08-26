package com.hit.geek.thackmaster.struct;

import com.hit.geek.thackmaster.action.Action;
import com.hit.geek.thackmaster.define.MarkerBean;
import com.hit.geek.thackmaster.view.View;

/**
 * Created by eason on 8/22/16.
 */
public class Element {
    private Action action = null;
    private View view = null;
    private MarkerBean data = null;

    public Element(Action action, View view, MarkerBean bean){
        this.action = action;
        this.view = view;
        this.data = bean;

        this.view.setElement(this);
    }

    public Action getAction() {
        return action;
    }

    public View getView() {
        return view;
    }

    public MarkerBean getData(){
        return data;
    }
}
