package com.citroen.commands;


import com.autowp.canreader.CanReaderService;
import com.autowp.canreader.TransmitCanFrame;

public class EnsureSource extends Command {
    private String source;

    public EnsureSource(CanReaderService canReaderService, String source) {
        super(canReaderService);
        this.source = source;
    }

    @Override
    protected TransmitCanFrame getNextCanFrame() {
        return null;
    }
}
