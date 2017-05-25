package com.citroen.handlers.buttons;

import android.view.KeyEvent;

import com.jvit.bus.Signal;


public class Rotator extends BaseButton {
    private static final Rotator ourInstance = new Rotator();
    double lastValue = 0;

    public Rotator() {
        super();
    }

    public static Rotator getInstance() {
        return ourInstance;
    }

    @Override
    public String getMessageId() {
        return "3e5";
    }

    @Override
    public String getSignalName() {
        return "Rotator Position";
    }

    @Override
    public int getKeyCode(Signal signal) {
        int key;
        if (signal.value > lastValue) {
            key = KeyEvent.KEYCODE_SOFT_LEFT;
        } else {
            key = KeyEvent.KEYCODE_SOFT_RIGHT;
        }
        lastValue = signal.value;
        return key;
    }
}
