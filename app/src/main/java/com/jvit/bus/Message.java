package com.jvit.bus;


import android.util.Log;

import com.autowp.can.CanFrame;
import com.autowp.can.CanMessage;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.BitSet;

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

    // Returns a bitset containing the values in bytes.
    // The byte-ordering of bytes must be big-endian which means the most significant bit is in element 0.
    public static BitSet fromByteArray(byte[] bytes) {
        BitSet bits = new BitSet();
        for (int i=0; i<bytes.length*8; i++) {
            if ((bytes[bytes.length-i/8-1]&(1<<(i%8))) > 0) {
                bits.set(i);
            }
        }
        return bits;
    }

    public ArrayList<Signal> parseFrame(CanMessage frame) {
        int bitCount = frame.getDLC() * 8;
        BitSet frameValue = fromByteArray(frame.getData());
        ArrayList<Signal> results = new ArrayList<>();
        for (Signal signal: this.signals) {
            int endBit = signal.startBit + signal.bitLength;
            if (endBit <= bitCount) {
                BitSet value = frameValue.get(bitCount - endBit, bitCount - signal.startBit);
                long[] values = value.toLongArray();
                if (values.length > 0) {
                    signal.parseValue(values[0]);
                } else {
                    signal.value = 0;
                }
                results.add(signal);
            } else {
                Log.d("CAN", String.format("Wrong Schema with id: 0x%03X", frame.getId()));
            }
        }

        return results;
    }

    public String toString() {
        return String.format("%h:%s", this.id, this.name);
    }
}
