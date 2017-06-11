package com.citroen.handlers.radioActivity;

import com.citroen.handlers.TextViewBaseSignalHandler;
import com.jvit.bus.Bus;
import com.jvit.bus.Signal;


public class RadioText extends TextViewBaseSignalHandler {
    private static final RadioText ourInstance = new RadioText();

    public static RadioText getInstance() {
        return ourInstance;
    }

    @Override
    public String getMessageId() {
        return "a4";
    }

    @Override
    public String getSignalName() {
        return "text";
    }
}
