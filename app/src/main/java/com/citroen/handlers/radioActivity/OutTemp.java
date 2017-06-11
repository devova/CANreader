package com.citroen.handlers.radioActivity;

import com.citroen.handlers.TextViewBaseSignalHandler;
import com.jvit.bus.Bus;
import com.jvit.bus.Signal;


public class OutTemp extends TextViewBaseSignalHandler {
    private static final OutTemp ourInstance = new OutTemp();

    public static OutTemp getInstance() {
        return ourInstance;
    }

    @Override
    public String getMessageId() {
        return "f6";
    }

    @Override
    public String getSignalName() {
        return "Out Temp";
    }

    @Override
    protected String getString(Signal signal, Bus bus) {
        if (signal.value > 0) {
            return String.format("%s \u2103", signal.getValue("%2.0f"));
        }
        return "0 \u2103";
    }
}
