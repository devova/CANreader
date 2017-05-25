package com.citroen.handlers.buttons;

import android.view.KeyEvent;

import com.jvit.bus.Signal;


public class Menu extends BaseButton {
    private static final Menu ourInstance = new Menu();

    public Menu() {
        super();
    }

    public static Menu getInstance() {
        return ourInstance;
    }

    @Override
    public String getMessageId() {
        return "3e5";
    }

    @Override
    public String getSignalName() {
        return "Menu";
    }

    @Override
    public int getKeyCode(Signal signal) {
        return KeyEvent.KEYCODE_MENU;
    }
}
