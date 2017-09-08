package com.autowp.canreader;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sccomponents.gauges.ScLinearGauge;


public class RadioTuneFragment extends Fragment {
    private ScLinearGauge gauge;


    public RadioTuneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_radio_tune, container, false);
        gauge = (ScLinearGauge) view.findViewById(R.id.tune);
        gauge.setLowValue((float) 100.8, 86, 108);
        gauge.setHighValue((float) 107.2, 86, 108);
        return view;
    }

}
