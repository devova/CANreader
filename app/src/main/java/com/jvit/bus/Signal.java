package com.jvit.bus;


import org.json.JSONException;
import org.json.JSONObject;

public class Signal {
    public String name;
    public int startBit;
    public int bitLength;
    public double factor = 1;
    public double offset = 0;
    public double value = 0;
    public String strValue;
    public Boolean isString = false;
    public JSONObject choices;


    public Signal(String name, int startBit, int bitLength) {
        this.name = name;
        this.startBit = startBit;
        this.bitLength = bitLength;
    }

    public void parseValue(long value) {
        this.value = value * this.factor + this.offset;
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
}
