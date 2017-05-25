package com.citroen.handlers.buttons;


import android.app.Instrumentation;

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
                    inst.sendKeyDownUpSync(getKeyCode(signal));
                }
            }).start();
        }
    }

    public abstract int getKeyCode(Signal signal);
}
