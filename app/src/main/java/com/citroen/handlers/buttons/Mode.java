package com.citroen.handlers.buttons;

import android.view.KeyEvent;

import com.jvit.bus.Signal;


public class Mode extends BaseButton {
    private static final Mode ourInstance = new Mode();

    public Mode() {
        super();
    }

    public static Mode getInstance() {
        return ourInstance;
    }

    @Override
    public String getMessageId() {
        return "3e5";
    }

    @Override
    public String getSignalName() {
        return "Mode";
    }

    @Override
    public boolean isKeyEvent() {
        return true;
    }

    @Override
    public KeyEvent getKeyEvent(Signal signal) {
        return new KeyEvent(1, 2, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_TAB, 0, KeyEvent.META_ALT_ON);
    }
}
