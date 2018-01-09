package com.autowp.canreader;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.autowp.can.CanAdapter;
import com.citroen.commands.EnsureSource;
import com.citroen.handlers.MediaSessionTextHandler;
import com.citroen.handlers.Source;
import com.citroen.handlers.ToastRadioFrequency;
import com.citroen.handlers.radioActivity.*;
import com.jvit.bus.Signal;

import java.util.Random;

public class RadioActivity extends ServiceConnectedActivity implements CanReaderService.OnMonitorChangedListener,
        CanReaderService.OnConnectionStateChangedListener {
    static public boolean active = false;
    RadioTuneFragment radioTune;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);

        Fragment tripFragment1 = getFragmentManager().findFragmentById(R.id.tripFragment1);

        radioTune = (RadioTuneFragment) getFragmentManager().findFragmentById(R.id.tuneFragment);
        findViewById(R.id.imageMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int random = new Random().nextInt((1080 - 800) + 1) + 800;
                radioTune.setFrequency((float) random/10);
            }
        });



    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        active = true;
    }

    protected void setHandlers() {
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/digital_7.ttf");
        canReaderService.bus.removeSignalHandler(
                ToastRadioFrequency.getInstance().setContext(getApplicationContext()));

        TextView textLarge = (TextView) findViewById(R.id.textLarge);
        textLarge.setTypeface(tf);
        Signal signal = canReaderService.bus.addSignalHandler(
                Frequency.getInstance().setRadioTuneFragment(radioTune).setView(textLarge));
        Frequency.getInstance().handle(signal, canReaderService.bus);

        TextView radioMem = (TextView) findViewById(R.id.radioMem);
        radioMem.setTypeface(tf);
        signal = canReaderService.bus.addSignalHandler(
                Memory.getInstance().setView(radioMem));
        Memory.getInstance().handle(signal, canReaderService.bus);

        TextView textSmall = (TextView) findViewById(R.id.textSmall);
        textSmall.setTypeface(tf);
        signal = canReaderService.bus.addSignalHandler(
                RadioText.getInstance().setView(textSmall));
        RadioText.getInstance().setLargeView(textLarge);
        RadioText.getInstance().handle(signal, canReaderService.bus);

        TextView textOutTemp = (TextView) findViewById(R.id.textOutTemp);
        textOutTemp.setTypeface(tf);
        signal = canReaderService.bus.addSignalHandler(
                Temp.getInstance().setView(textOutTemp).setContext(getApplicationContext()));
        Temp.getInstance().handle(signal, canReaderService.bus);

        TextView textCoolantTemp = (TextView) findViewById(R.id.textCoolantTemp);
        if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean(
                SettingsActivity.KEY_SHOW_COOLANT_TEMP, false)) {
            textCoolantTemp.setTypeface(tf);
            signal = canReaderService.bus.addSignalHandler(
                    CoolantTemp.getInstance().setView(textCoolantTemp).setContext(
                            getApplicationContext()));
            CoolantTemp.getInstance().handle(signal, canReaderService.bus);
            textCoolantTemp.setVisibility(View.VISIBLE);
        } else {
            canReaderService.bus.removeSignalHandler(CoolantTemp.getInstance());
            textCoolantTemp.setVisibility(View.INVISIBLE);
        }

        signal = canReaderService.bus.addSignalHandler(
                Source.getInstance().setContext(getApplicationContext()));
        Source.getInstance().handle(signal, canReaderService.bus);

        MediaSessionTextHandler.getInstance().setContext(getApplicationContext())
                .updateMetaData("VGOLOS", "107.2 MHz", "RADIO");
    }

    protected void unsetHandlers() {
        canReaderService.bus.removeSignalHandler(Memory.getInstance());
        canReaderService.bus.removeSignalHandler(Frequency.getInstance());
        canReaderService.bus.removeSignalHandler(Temp.getInstance());
        canReaderService.bus.removeSignalHandler(CoolantTemp.getInstance());
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
        active = false;
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
