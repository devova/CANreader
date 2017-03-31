package com.citroen.handlers;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.jvit.bus.Bus;
import com.jvit.bus.Signal;

public abstract class BaseSignalHandler implements Bus.SignalHandler {
    protected Context context;

    public BaseSignalHandler(Context ctx) {
        context = ctx;
    }

    @Override
    public Signal.SignalEventListener getListener() {
        return new Signal.SignalEventListener() {
            @Override
            public void handleSignalChanged(final Signal signal) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Your UI code here
                        handle(signal);
                    }
                });
            }
        };
    }

    abstract void handle(Signal signal);
}
