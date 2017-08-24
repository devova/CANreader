package com.autowp.canreader;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


abstract public class TripInfoFragment extends ServiceConnectedFragment{
    protected ArrayList<TextView> textViews = new ArrayList<>();

    public TripInfoFragment() {
        // Required empty public constructor
    }

    private void setTypeFace() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"fonts/digital_7.ttf");

        for (TextView tv: textViews) {
            tv.setTypeface(tf);
        }
    }

    abstract void setHandlers();

    @Override
    protected void afterConnect() {
        setHandlers();
        setTypeFace();
    }

    @Override
    protected void beforeDisconnect() {

    }
}
