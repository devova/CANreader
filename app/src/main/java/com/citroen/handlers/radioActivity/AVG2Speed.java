package com.citroen.handlers.radioActivity;

import com.citroen.handlers.TextViewBaseSignalHandler;
import com.jvit.bus.Bus;
import com.jvit.bus.Signal;


public class AVG2Speed extends TextViewBaseSignalHandler {
    private static final AVG2Speed ourInstance = new AVG2Speed();

    public static AVG2Speed getInstance() {
        return ourInstance;
    }

    @Override
    public String getMessageId() {
        return "2a1";
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
