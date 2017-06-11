package com.citroen.handlers.radioActivity;

import com.citroen.handlers.TextViewBaseSignalHandler;
import com.jvit.bus.Bus;
import com.jvit.bus.Signal;


public class AVG1Distance extends TextViewBaseSignalHandler {
    private static final AVG1Distance ourInstance = new AVG1Distance();

    public static AVG1Distance getInstance() {
        return ourInstance;
    }

    @Override
    public String getMessageId() {
        return "261";
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
