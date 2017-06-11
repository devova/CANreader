package com.autowp.canreader;

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
    protected void setHandlers() {
        TextView tripFuelConsumption = (TextView) getActivity().findViewById(R.id.tripFuelConsumption);
        Signal signal = canReaderService.bus.addSignalHandler(
                AVG2FuelConsumption.getInstance().setView(tripFuelConsumption));
        AVG2FuelConsumption.getInstance().handle(signal, null);

        TextView tripAvgSpeed = (TextView) getActivity().findViewById(R.id.tripAvgSpeed);
        signal = canReaderService.bus.addSignalHandler(
                AVG2Speed.getInstance().setView(tripAvgSpeed));
        AVG2Speed.getInstance().handle(signal, null);

        TextView tripDistance = (TextView) getActivity().findViewById(R.id.tripDistance);
        signal = canReaderService.bus.addSignalHandler(
                AVG2Distance.getInstance().setView(tripDistance));
        AVG2Distance.getInstance().handle(signal, null);
    }

}
