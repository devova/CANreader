package com.citroen.handlers;

import android.content.Context;
import android.widget.Toast;

import com.jvit.bus.Signal;


public class Volume extends BaseSignalHandler {

    public Volume(Context ctx) {
        super(ctx);
    }

    @Override
    public String getMessageId() {
        return "1a5";
    }

    @Override
    public String getSignalName() {
        return "Volume";
    }

    @Override
    void handle(Signal signal) {
        Toast.makeText(context, signal.toString(), Toast.LENGTH_LONG).show();
    }
}
