package com.citroen.handlers.radioActivity;

import com.citroen.handlers.TextViewBaseSignalHandler;
import com.jvit.bus.Bus;
import com.jvit.bus.Signal;


public class Memory extends TextViewBaseSignalHandler {
    private static final Memory ourInstance = new Memory();

    public static Memory getInstance() {
        return ourInstance;
    }

    @Override
    public String getMessageId() {
        return "225";
    }

    @Override
    public String getSignalName() {
        return "Position of band in memory";
    }

    @Override
    protected String getString(Signal signal, Bus bus) {
        if (signal.value > 0) {
            return String.format("MEM %s", signal.getValue("%2.0f"));
        }
        return "";
    }
}
