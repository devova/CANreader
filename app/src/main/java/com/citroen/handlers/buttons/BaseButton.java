package com.citroen.handlers.buttons;


import android.app.Instrumentation;
import android.view.KeyEvent;

import com.citroen.handlers.BaseSignalHandler;
import com.jvit.bus.Bus;
import com.jvit.bus.Signal;

public abstract class BaseButton extends BaseSignalHandler {
    @Override
    public void handle(final Signal signal, Bus bus) {
        if (signal.value > 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Instrumentation inst = new Instrumentation();
                    if (isKeyEvent()){
                        inst.sendKeySync(getKeyEvent(signal));
                    } else {
                        inst.sendKeyDownUpSync(getKeyCode(signal));
                    }
                }
            }).start();
        }
    }

    public int getKeyCode(Signal signal) {
        return 0;
    }

    public KeyEvent getKeyEvent(Signal signal) {
        return null;
    }

    public boolean isKeyEvent() {
        return false;
    }
}
