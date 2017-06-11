package com.citroen.handlers.radioActivity;

import com.citroen.handlers.TextViewBaseSignalHandler;
import com.jvit.bus.Bus;
import com.jvit.bus.Signal;


public class AVG2Distance extends TextViewBaseSignalHandler {
    private static final AVG2Distance ourInstance = new AVG2Distance();

    public static AVG2Distance getInstance() {
        return ourInstance;
    }

    @Override
    public String getMessageId() {
        return "2a1";
    }

    @Override
    public String getSignalName() {
        return "Distance";
    }

    @Override
    protected String getString(Signal signal, Bus bus) {
        return String.format("%s km", super.getString(signal, bus));
    }
}
