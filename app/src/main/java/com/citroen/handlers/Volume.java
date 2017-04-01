package com.citroen.handlers;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.autowp.canreader.R;
import com.jvit.bus.Signal;


public class Volume extends BaseSignalHandler {
    private Toast toast;

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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.audio_volume, null);

        ProgressBar progressBar = (ProgressBar) layout.findViewById(R.id.progressBar);
        progressBar.setProgress((int) signal.value);

        if (toast == null) {
            toast = new Toast(context);
        }
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
