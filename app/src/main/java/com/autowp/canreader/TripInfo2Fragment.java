package com.autowp.canreader;

import android.widget.TextView;

import com.citroen.handlers.radioActivity.AVG1Distance;
import com.citroen.handlers.radioActivity.AVG1FuelConsumption;
import com.citroen.handlers.radioActivity.AVG1Speed;
import com.jvit.bus.Signal;

public class TripInfo2Fragment extends TripInfoFragment{

    public TripInfo2Fragment() {
        // Required empty public constructor
    }

    @Override
    protected void setHandlers() {
        TextView tripFuelConsumption = (TextView) getActivity().findViewById(R.id.tripFuelConsumption);
        Signal signal = canReaderService.bus.addSignalHandler(
                AVG1FuelConsumption.getInstance().setView(tripFuelConsumption));
        AVG1FuelConsumption.getInstance().handle(signal, null);

        TextView tripAvgSpeed = (TextView) getActivity().findViewById(R.id.tripAvgSpeed);
        signal = canReaderService.bus.addSignalHandler(
                AVG1Speed.getInstance().setView(tripAvgSpeed));
        AVG1Speed.getInstance().handle(signal, null);

        TextView tripDistance = (TextView) getActivity().findViewById(R.id.tripDistance);
        signal = canReaderService.bus.addSignalHandler(
                AVG1Distance.getInstance().setView(tripDistance));
        AVG1Distance.getInstance().handle(signal, null);
    }

}
