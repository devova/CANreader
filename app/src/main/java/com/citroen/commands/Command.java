package com.citroen.commands;


import com.autowp.canreader.CanReaderService;
import com.autowp.canreader.TransmitCanFrame;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class Command {
    private CanReaderService canReaderService;
    private ScheduledExecutorService threadsPool = Executors.newScheduledThreadPool(1);
    private class TransmitRunnable implements Runnable {

        private TransmitCanFrame frame;

        public TransmitRunnable(TransmitCanFrame frame) {
            this.frame = frame;
        }

        @Override
        public void run() {
            if (frame.isEnabled()) {
                canReaderService.transmit(frame);
            }
        }
    }

    public Command(CanReaderService canReaderService) {
        this.canReaderService = canReaderService;
    }

    abstract protected TransmitCanFrame getNextCanFrame();

    public void execute() {
        TransmitCanFrame frame = getNextCanFrame();
        while (frame != null) {
            canReaderService.transmit(frame);
            Thread.sleep(frame.period)
            TransmitCanFrame frame = getNextCanFrame();
        }
//        TransmitRunnable runnable = new TransmitRunnable(frame);
//        Future<?> future = threadsPool.schedule(runnable, 0, TimeUnit.MILLISECONDS);
//        future.
    }
}
