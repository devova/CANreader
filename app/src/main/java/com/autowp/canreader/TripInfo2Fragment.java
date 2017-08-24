package com.autowp.canreader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.citroen.handlers.radioActivity.AVG2Distance;
import com.citroen.handlers.radioActivity.AVG2FuelConsumption;
import com.citroen.handlers.radioActivity.AVG2Speed;
import com.jvit.bus.Signal;

public class TripInfo2Fragment extends TripInfoFragment{

    public TripInfo2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trip_info_2, container, false);
    }

    @Override
    protected void setHandlers() {
        TextView tripFuelConsumption = (TextView) getView().findViewById(R.id.tripFuelConsumption2);
        textViews.add(tripFuelConsumption);
        Signal signal = canReaderService.bus.addSignalHandler(
                AVG2FuelConsumption.getInstance().setView(tripFuelConsumption));
        AVG2FuelConsumption.getInstance().handle(signal, canReaderService.bus);

        TextView tripAvgSpeed = (TextView) getActivity().findViewById(R.id.tripAvgSpeed2);
        textViews.add(tripAvgSpeed);
        signal = canReaderService.bus.addSignalHandler(
                AVG2Speed.getInstance().setView(tripAvgSpeed));
        AVG2Speed.getInstance().handle(signal, canReaderService.bus);

        TextView tripDistance = (TextView) getActivity().findViewById(R.id.tripDistance2);
        textViews.add(tripDistance);
        signal = canReaderService.bus.addSignalHandler(
                AVG2Distance.getInstance().setView(tripDistance));
        AVG2Distance.getInstance().handle(signal, canReaderService.bus);
    }

}
