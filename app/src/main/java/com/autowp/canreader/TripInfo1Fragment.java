package com.autowp.canreader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.citroen.handlers.radioActivity.AVG1Distance;
import com.citroen.handlers.radioActivity.AVG1FuelConsumption;
import com.citroen.handlers.radioActivity.AVG1Speed;
import com.jvit.bus.Signal;

public class TripInfo1Fragment extends TripInfoFragment{

    public TripInfo1Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trip_info_1, container, false);
    }

    @Override
    protected void setHandlers() {
        TextView tripFuelConsumption = (TextView) getView().findViewById(R.id.tripFuelConsumption1);
        textViews.add(tripFuelConsumption);
        Signal signal = canReaderService.bus.addSignalHandler(
                AVG1FuelConsumption.getInstance().setView(tripFuelConsumption));
        AVG1FuelConsumption.getInstance().handle(signal, canReaderService.bus);

        TextView tripAvgSpeed = (TextView) getActivity().findViewById(R.id.tripAvgSpeed1);
        textViews.add(tripAvgSpeed);
        signal = canReaderService.bus.addSignalHandler(
                AVG1Speed.getInstance().setView(tripAvgSpeed));
        AVG1Speed.getInstance().handle(signal, canReaderService.bus);

        TextView tripDistance = (TextView) getActivity().findViewById(R.id.tripDistance1);
        textViews.add(tripDistance);
        signal = canReaderService.bus.addSignalHandler(
                AVG1Distance.getInstance().setView(tripDistance));
        AVG1Distance.getInstance().handle(signal, canReaderService.bus);
    }

}
