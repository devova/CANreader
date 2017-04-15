package com.citroen.handlers;


import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.autowp.canreader.R;
import com.jvit.bus.Bus;
import com.jvit.bus.Message;
import com.jvit.bus.Signal;

public class ToastRadioFrequency extends BaseSignalHandler {
    private static final ToastRadioFrequency ourInstance = new ToastRadioFrequency();
    private Toast toast;
    private View layout;
    public ToastRadioFrequency() {
        super();
        createLayout();
        createToast();
    }

    public static ToastRadioFrequency getInstance() {
        return ourInstance;
    }

    @Override
    public String getMessageId() {
        return "225";
    }

    @Override
    public String getSignalName() {
        return "Frequency";
    }

    @Override
    public void handle(Signal signal, Bus bus) {
        Message message = bus.messages.get(getMessageId());

        TextView textFrequency = (TextView) layout.findViewById(R.id.textFrequency);
        textFrequency.setText(signal.getValue());

        TextView textMode = (TextView) layout.findViewById(R.id.textMode);
        textMode.setText(message.signals.get("Radio band").getValue());

        int mem = (int) message.signals.get("Position of band in memory").value;
        TextView textMem = (TextView) layout.findViewById(R.id.textMem);
        textMem.setText(mem > 0 ? String.format("MEM %d", mem) : "");

        toast.show();
    }

    private void createLayout() {
        Typeface tf = Typeface.createFromAsset(context.getAssets(),"fonts/digital_7.ttf");
        Typeface tfi = Typeface.createFromAsset(context.getAssets(),"fonts/digital_7_italic.ttf");
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.toast_radio_frequency, null);
        TextView textFrequency = (TextView) layout.findViewById(R.id.textFrequency);
        textFrequency.setTypeface(tf);

        TextView textMode = (TextView) layout.findViewById(R.id.textMode);
        textMode.setTypeface(tfi);

        TextView textMem = (TextView) layout.findViewById(R.id.textMem);
        textMem.setTypeface(tfi);
    }

    private void createToast() {
        toast = new Toast(context);
        toast.setGravity(Gravity.RIGHT | Gravity.TOP, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
    }
}
