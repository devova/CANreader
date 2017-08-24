package com.citroen.handlers.radioActivity;

import com.autowp.canreader.SettingsActivity;
import com.citroen.handlers.TextViewBaseSignalHandler;
import com.jvit.bus.Bus;
import com.jvit.bus.Signal;


public class Temp extends TextViewBaseSignalHandler {
    private static final Temp ourInstance = new Temp();

    public static Temp getInstance() {
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
        String outTemp;
        if (signal.value > 0) {
            outTemp = String.format("%s \u2103", signal.getValue("%2.0f"));
        } else {
            outTemp = "0 \u2103";
        }
        if (getSharedPreferences().getBoolean(SettingsActivity.KEY_SHOW_COOLANT_TEMP, false)) {
            Signal coolantSignal = bus.messages.get(getMessageId()).signals.get("Temperature Coolant");
            if (coolantSignal.value > 0) {
                outTemp = String.format("Out: %s/ Coolant: %s \u2103", outTemp, coolantSignal.getValue("%2.0f"));
            }
        }
        return outTemp;
    }
}
