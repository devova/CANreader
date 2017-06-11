package com.citroen.handlers.radioActivity;

import com.citroen.handlers.TextViewBaseSignalHandler;
import com.jvit.bus.Bus;
import com.jvit.bus.Signal;


public class AVG1Speed extends TextViewBaseSignalHandler {
    private static final AVG1Speed ourInstance = new AVG1Speed();

    public static AVG1Speed getInstance() {
        return ourInstance;
    }

    @Override
    public String getMessageId() {
        return "261";
    }

    @Override
    public String getSignalName() {
        return "AVG Speed";
    }

    @Override
    protected String getString(Signal signal, Bus bus) {
        return String.format("%s km/h", super.getString(signal, bus));
    }
}
