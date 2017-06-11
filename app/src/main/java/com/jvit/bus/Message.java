package com.jvit.bus;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import com.autowp.can.CanMessage;

import org.apache.commons.lang.ArrayUtils;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Message {

    public HashMap<String, Signal> signals = new HashMap<>();

    public String name;
    public int id;
    private boolean multiframe = false;

    private Boolean forceParsing = false;

    public Message(String name, String idHex, boolean multiframe) {
        this.name = name;
        this.id = Integer.decode(idHex);
        this.multiframe = multiframe;
    }

    public String getId() {
        return String.format("%h", this.id);
    }

    public void addSignal(Signal signal) {
        this.signals.put(signal.name, signal);
    }

    public ArrayList<Signal> getSignals() {
        return new ArrayList<>(signals.values());
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

    public Boolean shouldParse() {
        for (Signal signal: this.getSignals()) {
            if (signal.shouldParse()) {
                return true;
            }
        }
        return false;
    }

    public boolean isMultiframe() {
        return multiframe;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public ArrayList<Signal> parseMessage(CanMessage message, Bus bus) {
        int bitCount = message.getDLC() * 8;
        BitSet frameValue = fromByteArray(message.getData());
        ArrayList<Signal> results = new ArrayList<>();
        for (Signal signal: getSignals()) {
            if (!forceParsing && !signal.shouldParse()) {
                continue;
            }
            int endBit;
            if (signal.bitLength == -1) {
                endBit = bitCount;
            } else {
                endBit = signal.startBit + signal.bitLength;
            }
            if (endBit <= bitCount) {
                BitSet value = frameValue.get(bitCount - endBit, bitCount - signal.startBit);
                long[] values = value.toLongArray();
                if (values.length > 0) {
                    signal.parseValue(values[0]);
                } else {
                    signal.value = 0;
                }
                if (signal.isString) {
                    if (signal.choices != null) {
                        String key = String.format("%1.0f", signal.value);
                        if (signal.choices.has(key)) {
                            try {
                                signal.strValue = signal.choices.getString(key);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        byte[] byteArr = value.toByteArray();
                        ArrayUtils.reverse(byteArr);
                        signal.strValue = new String(byteArr);
                    }
                }
                results.add(signal);
            } else {
                Log.d("CAN", String.format("Wrong Schema with id: 0x%03X", message.getId()));
            }
        }
        for (Signal signal: getSignals()) {
            // trigger signal after they all has parsed
            signal.triggerChangeEvent(bus);
        }

        return results;
    }

    public void turnOnForceParsing() {
        forceParsing = true;
    }

    public void turnOffForceParsing() {
        forceParsing = false;
    }

    public String toString() {
        return String.format("%h:%s", this.id, this.name);
    }
}
