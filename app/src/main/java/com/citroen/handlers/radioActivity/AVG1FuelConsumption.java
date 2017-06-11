package com.citroen.handlers.radioActivity;

import com.citroen.handlers.TextViewBaseSignalHandler;
import com.jvit.bus.Bus;
import com.jvit.bus.Signal;


public class AVG1FuelConsumption extends TextViewBaseSignalHandler {
    private static final AVG1FuelConsumption ourInstance = new AVG1FuelConsumption();

    public static AVG1FuelConsumption getInstance() {
        return ourInstance;
    }

    @Override
    public String getMessageId() {
        return "261";
    }

    @Override
    public String getSignalName() {
        return "Liters per 100km";
    }

    @Override
    protected String getString(Signal signal, Bus bus) {
        return String.format("%s l/100km", super.getString(signal, bus));
    }
}
