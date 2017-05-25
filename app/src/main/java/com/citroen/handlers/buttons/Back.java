package com.citroen.handlers.buttons;

import android.view.KeyEvent;

import com.jvit.bus.Signal;


public class Back extends BaseButton {
    private static final Back ourInstance = new Back();

    public Back() {
        super();
    }

    public static Back getInstance() {
        return ourInstance;
    }

    @Override
    public String getMessageId() {
        return "3e5";
    }

    @Override
    public String getSignalName() {
        return "Back";
    }

    @Override
    public int getKeyCode(Signal signal) {
        return KeyEvent.KEYCODE_BACK;
    }
}
