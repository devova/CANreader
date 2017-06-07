package com.citroen.commands;

import com.autowp.can.CanFrame;
import com.autowp.can.CanFrameException;
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
    protected TransmitCanFrame getNextCanFrame(){
        if (!Source.getInstance().getLastValue().equals(source)) {
            byte[] data = {0x02, 0, 0};
            TransmitCanFrame frame = null;
            try {
                frame = new TransmitCanFrame(
                        new CanFrame(Integer.parseInt("21f", 16), data, false),
                        550);
            } catch (CanFrameException e) {
                e.printStackTrace();
            }
            return frame;
        }
        return null;
    }
}
