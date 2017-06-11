package com.autowp.canreader;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.autowp.can.CanAdapter;
import com.citroen.commands.EnsureSource;
import com.citroen.handlers.Source;
import com.citroen.handlers.ToastRadioFrequency;
import com.citroen.handlers.radioActivity.*;
import com.jvit.bus.Signal;

public class RadioActivity extends ServiceConnectedActivity implements CanReaderService.OnMonitorChangedListener,
        CanReaderService.OnConnectionStateChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

//        TextView radioFreq = (TextView) findViewById(R.id.radioFrequency);
//        radioFreq.setText(ToastRadioFrequency.getInstance().getLastValue());

    }

    protected void setHandlers() {
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/digital_7.ttf");
        canReaderService.bus.removeSignalHandler(
                ToastRadioFrequency.getInstance().setContext(getApplicationContext()));

        TextView radioFreq = (TextView) findViewById(R.id.radioFrequency);
        radioFreq.setTypeface(tf);
        Signal signal = canReaderService.bus.addSignalHandler(
                Frequency.getInstance().setView(radioFreq));
        Frequency.getInstance().handle(signal, null);

        TextView radioMem = (TextView) findViewById(R.id.radioMem);
        radioMem.setTypeface(tf);
        signal = canReaderService.bus.addSignalHandler(
                Memory.getInstance().setView(radioMem));
        Memory.getInstance().handle(signal, null);
    }

    protected void unsetHandlers() {
        canReaderService.bus.removeSignalHandler(Memory.getInstance());
        canReaderService.bus.removeSignalHandler(Frequency.getInstance());
        canReaderService.bus.addSignalHandler(ToastRadioFrequency.getInstance());
    }

    @Override
    protected void afterConnect() {
        canReaderService.addListener((CanReaderService.OnMonitorChangedListener) this);
        canReaderService.addListener((CanReaderService.OnConnectionStateChangedListener) this);
        setHandlers();
    }

    @Override
    protected void beforeDisconnect() {
        unsetHandlers();
        canReaderService.removeListener((CanReaderService.OnConnectionStateChangedListener) this);
        canReaderService.removeListener((CanReaderService.OnMonitorChangedListener) this);
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

    @Override
    public void handleConnectedStateChanged(CanAdapter.ConnectionState connection) {
        if (connection == CanAdapter.ConnectionState.CONNECTED) {
            EnsureSource command = new EnsureSource(canReaderService,"Tuner");
            command.execute();
        }
    }
}
