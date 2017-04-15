package com.citroen.handlers;

import android.widget.TextView;

import com.jvit.bus.Bus;
import com.jvit.bus.Signal;


public abstract class TextViewBaseSignalHandler extends BaseSignalHandler {
    private TextView textView;

    public TextViewBaseSignalHandler setView(TextView view) {
        textView = view;
        return this;
    }

    @Override
    public void handle(Signal signal, Bus bus) {
        textView.setText(getString(signal, bus));
    }

    protected String getString(Signal signal, Bus bus) {
        return signal.getValue();
    }
}
