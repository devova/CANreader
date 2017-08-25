package com.citroen.handlers.radioActivity;

import android.widget.TextView;

import com.citroen.handlers.TextViewBaseSignalHandler;
import com.jvit.bus.Bus;
import com.jvit.bus.Signal;


public class RadioText extends TextViewBaseSignalHandler {
    private TextView frequencyTextView;
    private static final RadioText ourInstance = new RadioText();

    public static RadioText getInstance() {
        return ourInstance;
    }

    public void setFrequencyView(TextView view) {
        frequencyTextView = view;
    }

    @Override
    public String getMessageId() {
        return "a4";
    }

    @Override
    public String getSignalName() {
        return "text";
    }

    @Override
    public void handle(Signal signal, Bus bus) {
        Signal type = bus.messages.get(getMessageId()).signals.get("type");
        if (type.value > 0) {
            if (type.value == 50) {
                super.handle(signal, bus);
            } else {
                frequencyTextView.setText(getString(signal, bus));
            }
        }
    }
}
