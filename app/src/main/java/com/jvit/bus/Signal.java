package com.jvit.bus;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Signal {

    public interface SignalEventListener {
        void handleSignalChanged(Signal signal);
    }

    public String name;
    public int startBit;
    public int bitLength;
    public double factor = 1;
    public double offset = 0;
    public double value = 0;
    public String strValue;
    public Boolean isString = false;
    public JSONObject choices;

    private List<SignalEventListener> listeners = new ArrayList<>();
    private Boolean hasChanged = false;


    public Signal(String name, int startBit, int bitLength) {
        this.name = name;
        this.startBit = startBit;
        this.bitLength = bitLength;
    }

    void parseValue(long value) {
        double oldValue = this.value;
        this.value = value * this.factor + this.offset;
        if (!hasChanged && this.value != oldValue) {
            hasChanged = true;
        }
    }

    public String getValue() {
        return this.isString ? this.strValue : String.format("%2.1f", this.value);
    }

    public String toString() {
        return String.format("%s: %s", this.name, this.getValue());
    }

    public String toDocString() {
        String repr = String.format("%s: start=%d length=%d",
                this.name, this.startBit, this.bitLength);
        if (this.factor != 1) {
            repr += String.format(", factor=%3.2f", this.factor);
        }
        if (this.offset != 0) {
            repr += String.format(", offset=%2.1f", this.offset);
        }
        repr += String.format(", value=%s", this.getValue());
        if (this.choices != null) {
            try {
                repr += String.format("\nchoices: %s", this.choices.toString(2));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return repr;
    }

    public void addEventListener(SignalEventListener listener) {
        listeners.add(listener);
    }

    public void removeEventListener(SignalEventListener listener) {
        listeners.remove(listener);
    }

    Boolean shouldParse() {
        return listeners.size() > 0;
    }

    void triggerChangeEvent() {
        if (hasChanged) {
            hasChanged = false;
            for (SignalEventListener listener: listeners) {
                listener.handleSignalChanged(this);
            }
        }
    }
}
