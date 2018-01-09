package com.citroen.handlers.radioActivity;

import com.citroen.handlers.TextViewBaseSignalHandler;
import com.jvit.bus.Bus;
import com.jvit.bus.Signal;


public class CoolantTemp extends TextViewBaseSignalHandler {
    private static final CoolantTemp ourInstance = new CoolantTemp();

    public static CoolantTemp getInstance() {
        return ourInstance;
    }

    @Override
    public String getMessageId() {
        return "f6";
    }

    @Override
    public String getSignalName() {
        return "Temperature Coolant";
    }

    @Override
    protected String getString(Signal signal, Bus bus) {
        String coolantTemp;
        if (signal.value > 0) {
            coolantTemp = String.format("%s \u2103", signal.getValue("%2.0f"));
        } else {
            coolantTemp = "0 \u2103";
        }
        return coolantTemp;
    }
}
