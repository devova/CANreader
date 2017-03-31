package com.citroen.handlers;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.jvit.bus.Bus;
import com.jvit.bus.Signal;


public class Volume implements Bus.SignalHandler {
    private Context context;

    public Volume(Context cntxt) {
        context = cntxt;
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
    public Signal.SignalEventListener getListener() {
        return new Signal.SignalEventListener() {
            @Override
            public void handleSignalChanged(final Signal signal) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Your UI code here
                        Toast.makeText(context, signal.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        };
    }
}
