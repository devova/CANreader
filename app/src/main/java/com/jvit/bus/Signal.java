package com.jvit.bus;


public class Signal {
    public String name;
    public int startBit;
    public int bitLength;
    public double factor = 1;
    public double offset = 0;
    public double value = 0;

    public Signal(String name, int startBit, int bitLength) {
        this.name = name;
        this.startBit = startBit;
        this.bitLength = bitLength;
    }

    public Signal(String name, int startBit, int bitLength, double factor) {
        this.name = name;
        this.startBit = startBit;
        this.bitLength = bitLength;
        this.factor = factor;
    }

    public Signal(String name, int startBit, int bitLength, double factor, int offset) {
        this.name = name;
        this.startBit = startBit;
        this.bitLength = bitLength;
        this.factor = factor;
        this.offset = offset;
    }

    public void parseValue(int value) {
        this.value = value * this.factor + this.offset;
    }

    public String toString() {
        return String.format("%s: %4.3f", this.name, this.value);
    }
}
