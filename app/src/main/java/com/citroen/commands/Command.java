package com.citroen.commands;


import com.autowp.canreader.CanReaderService;
import com.autowp.canreader.TransmitCanFrame;

public abstract class Command {
    private CanReaderService canReaderService;

    public Command(CanReaderService canReaderService) {
        this.canReaderService = canReaderService;
    }

    abstract protected TransmitCanFrame getNextCanFrame();

    public void execute() {
        TransmitCanFrame frame = getNextCanFrame();
        while (frame != null) {
            canReaderService.transmit(frame);
            try {
                Thread.sleep(frame.getPeriod());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            frame = getNextCanFrame();
        }
    }
}
