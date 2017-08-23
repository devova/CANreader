package com.citroen.handlers;

import android.content.Intent;
import android.content.SharedPreferences;

import com.autowp.canreader.RadioActivity;
import com.autowp.canreader.SettingsActivity;
import com.jvit.bus.Bus;
import com.jvit.bus.Signal;


public class Source extends BaseSignalHandler {
    private static final Source ourInstance = new Source();

    public Source() {
        super();
    }

    public static Source getInstance() {
        return ourInstance;
    }

    @Override
    public String getMessageId() {
        return "165";
    }

    @Override
    public String getSignalName() {
        return "Source";
    }

    @Override
    public void handle(Signal signal, Bus bus) {
        if (signal.getValue().equals("Tuner") && !RadioActivity.active) {
            SharedPreferences sharedPref = getSharedPreferences();
            Boolean show = sharedPref.getBoolean(SettingsActivity.KEY_AUTO_START_RADIO, true);
            if (show) {
                Intent intent = new Intent(context, RadioActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }
    }
}
