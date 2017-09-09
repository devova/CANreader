package com.citroen.handlers.radioActivity;

import com.autowp.canreader.RadioTuneFragment;
import com.citroen.handlers.TextViewBaseSignalHandler;
import com.jvit.bus.Bus;
import com.jvit.bus.Signal;


public class Frequency extends TextViewBaseSignalHandler {
    private static final Frequency ourInstance = new Frequency();
    private RadioTuneFragment radioTune;
    public static Frequency getInstance() {
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

    public Frequency setRadioTuneFragment(RadioTuneFragment radioTune) {
        this.radioTune = radioTune;
        return this;
    }

    @Override
    protected String getString(Signal signal, Bus bus) {
        return String.format("%s MHz", super.getString(signal, bus));
    }

    @Override
    public void handle(Signal signal, Bus bus) {
        radioTune.setFrequency((float) signal.value);
    }
}
