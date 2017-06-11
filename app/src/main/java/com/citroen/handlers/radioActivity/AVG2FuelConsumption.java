package com.citroen.handlers.radioActivity;

import com.citroen.handlers.TextViewBaseSignalHandler;
import com.jvit.bus.Bus;
import com.jvit.bus.Signal;


public class AVG2FuelConsumption extends TextViewBaseSignalHandler {
    private static final AVG2FuelConsumption ourInstance = new AVG2FuelConsumption();

    public static AVG2FuelConsumption getInstance() {
        return ourInstance;
    }

    @Override
    public String getMessageId() {
        return "2a1";
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
