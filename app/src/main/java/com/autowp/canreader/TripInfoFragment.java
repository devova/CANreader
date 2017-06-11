package com.autowp.canreader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


abstract public class TripInfoFragment extends ServiceConnectedFragment{

    public TripInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trip_info, container, false);
    }

    abstract void setHandlers();

    @Override
    protected void afterConnect() {
        setHandlers();
    }

    @Override
    protected void beforeDisconnect() {

    }
}
