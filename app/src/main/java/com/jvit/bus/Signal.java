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

    public void parseValue(int value) {
        this.value = value * this.factor + this.offset;
    }

    public String toString() {
        return String.format("%s: %2.1f", this.name, this.value);
    }

    public String toDocString() {
        return String.format("%s: start=%d length=%d factor=%2.1f offset=%2.1f",
                this.name, this.startBit, this.bitLength, this.factor, this.offset);
    }
}
