package com.autowp.can;

import android.content.res.Resources;

import com.autowp.canreader.R;
import com.jvit.bus.Bus;
import com.jvit.parser.JsonParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CanBusSpecs {
    protected int speed; // kbit
    
    protected int[] multiframeAbitrationID = new int[0];

    public Bus bus;

    public CanBusSpecs(Resources resources) {
        //Get Data From Text Resource File Contains Json Data.
        InputStream inputStream = resources.openRawResource(R.raw.can_bus_schema);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonParser jsonParser = new JsonParser();
        bus = jsonParser.parse(byteArrayOutputStream);
    }
    
    public int getSpeed()
    {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    public boolean isMultiFrame(int id)
    {
        boolean result = false;
        for (int i=0; i<multiframeAbitrationID.length; i++) {
            if (multiframeAbitrationID[i] == id) {
                result = true;
                break;
            }
        }
        
        return result;
    }
}
