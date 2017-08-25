package com.citroen.handlers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.widget.Toast;

import com.autowp.canreader.RadioActivity;
import com.autowp.canreader.SettingsActivity;
import com.jvit.bus.Bus;
import com.jvit.bus.Signal;


public class Source extends BaseSignalHandler implements AudioManager.OnAudioFocusChangeListener {
    private static final Source ourInstance = new Source();
    private AudioManager audioManager;

    public Source() {
        super();
    }

    public static Source getInstance() {
        return ourInstance;
    }

    @Override
    public String getMessageId() {
        return "165";
    }

    @Override
    public String getSignalName() {
        return "Source";
    }

    @Override
    public void handle(Signal signal, Bus bus) {
        if (signal.getValue().equals("Tuner")) {
            requestAudioFocus();
            if (!RadioActivity.active) {
                SharedPreferences sharedPref = getSharedPreferences();
                Boolean show = sharedPref.getBoolean(SettingsActivity.KEY_AUTO_START_RADIO, true);
                if (show) {
                    Intent intent = new Intent(context, RadioActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        }
    }

    private boolean requestAudioFocus() {
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            //Focus gained
            return true;
        }
        //Could not gain focus
        return false;
    }

    @Override
    public void onAudioFocusChange(int focusState) {
        //Invoked when the audio focus of the system is updated.
        switch (focusState) {
            case AudioManager.AUDIOFOCUS_GAIN:
                // resume playback
                Toast.makeText(context, "Oh my PLAY", Toast.LENGTH_SHORT).show();
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                // Lost focus for an unbounded amount of time: stop playback and release media player
                Toast.makeText(context, "Oh my STOP", Toast.LENGTH_SHORT).show();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                // Lost focus for a short time, but we have to stop
                // playback. We don't release the media player because playback
                // is likely to resume
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                // Lost focus for a short time, but it's ok to keep playing
                // at an attenuated level
                break;
        }
    }
}
