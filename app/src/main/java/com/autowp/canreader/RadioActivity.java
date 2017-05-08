package com.autowp.canreader;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.citroen.commands.EnsureSource;
import com.citroen.handlers.Source;
import com.citroen.handlers.ToastRadioFrequency;
import com.citroen.handlers.radioActivity.*;

public class RadioActivity extends ServiceConnectedActivity implements CanReaderService.OnMonitorChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (!Source.getInstance().getLastValue().equals("Tuner")) {
            EnsureSource command = new EnsureSource(canReaderService,"Tuner");
            command.execute();
        }
    }

    protected void setHandlers() {
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/digital_7.ttf");
        canReaderService.bus.removeSignalHandler(
                ToastRadioFrequency.getInstance().setContext(getApplicationContext()));

        TextView radioFreq = (TextView) findViewById(R.id.radioFrequency);
        radioFreq.setTypeface(tf);
        canReaderService.bus.addSignalHandler(
                Frequency.getInstance().setView(radioFreq));

        TextView radioMem = (TextView) findViewById(R.id.radioMem);
        radioMem.setTypeface(tf);
        canReaderService.bus.addSignalHandler(
                Memory.getInstance().setView(radioMem));
    }

    protected void unsetHandlers() {
        canReaderService.bus.removeSignalHandler(Memory.getInstance());
        canReaderService.bus.removeSignalHandler(Frequency.getInstance());
        canReaderService.bus.addSignalHandler(ToastRadioFrequency.getInstance());
    }

    @Override
    protected void afterConnect() {
        canReaderService.addListener(this);
        setHandlers();
    }

    @Override
    protected void beforeDisconnect() {
        unsetHandlers();
        canReaderService.removeListener(this);
    }

    @Override
    public void handleMonitorUpdated() {

    }

    @Override
    public void handleMonitorUpdated(MonitorCanMessage message) {

    }

    @Override
    public void handleSpeedChanged(double speed) {

    }
}
