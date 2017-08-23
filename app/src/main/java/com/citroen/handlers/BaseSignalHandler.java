package com.citroen.handlers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;

import com.jvit.bus.Bus;
import com.jvit.bus.Signal;

public abstract class BaseSignalHandler implements Bus.SignalHandler {
    protected Context context;
    private Signal.SignalEventListener listener;
    private String lastValue = "";

    public BaseSignalHandler() {}

    public BaseSignalHandler setContext(Context ctx) {
        context = ctx;
        return this;
    }

    public SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getLastValue() {
        return lastValue;
    }

    @Override
    public Signal.SignalEventListener getListener() {
        if (listener == null) {
            listener = new Signal.SignalEventListener() {
                @Override
                public void handleSignalChanged(final Signal signal, final Bus bus) {
                    lastValue = signal.getValue();
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Your UI code here
                            handle(signal, bus);
                        }
                    });
                }
            };
        }
        return listener;
    }

    public abstract void handle(Signal signal, Bus bus);
}
