package com.jvit.bus;


import com.autowp.can.CanFrame;
import com.autowp.can.CanMessage;

import java.util.ArrayList;

public class Message {
    public ArrayList<Signal> signals = new ArrayList<>();

    public String name;
    public int id;

    public Message(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Message(String name, String idHex) {
        this.name = name;
        this.id = Integer.decode(idHex);
    }

    public void addSignal(Signal signal) {
        this.signals.add(signal);
    }

    public ArrayList<Signal> parseFrame(CanMessage frame) {
        long frameValue = 0;

        for (int i = 0; i < frame.getDLC(); i++) {
            frameValue = frameValue + (frame.getData()[i] << 8 * i);
        }

        ArrayList<Signal> results = new ArrayList<>();
        for (Signal signal: this.signals) {
            int endBit = signal.startBit + signal.bitLength;
            // compute the mask
            long mask = 0;
            for (int i = signal.startBit; i <= endBit; i++) {
                mask = mask + (int) Math.pow(2, i);
            }
            int value = (int) (frameValue & mask) >> signal.startBit;
            signal.parseValue(value);
            results.add(signal);
        }

        return results;
    }

    public String toString() {
        return String.format("%h:%s", this.id, this.name);
    }
}
