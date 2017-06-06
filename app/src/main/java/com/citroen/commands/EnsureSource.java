package com.citroen.commands;

import com.autowp.can.CanFrame;
import com.autowp.canreader.CanReaderService;
import com.autowp.canreader.TransmitCanFrame;
import com.citroen.handlers.Source;

public class EnsureSource extends Command {
    private String source;

    public EnsureSource(CanReaderService canReaderService, String source) {
        super(canReaderService);
        this.source = source;
    }

    @Override
    protected TransmitCanFrame getNextCanFrame() {
        if (!Source.getInstance().getLastValue().equals("Tuner")) {
            TransmitCanFrame frame = new TransmitCanFrame(new CanFrame(12, [12,12]), 550);
            return frame;
        }
        return null;
    }
}
