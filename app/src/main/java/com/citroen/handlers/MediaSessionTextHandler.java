package com.citroen.handlers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadata;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.widget.Toast;

import com.autowp.canreader.R;
import com.jvit.bus.Bus;
import com.jvit.bus.Signal;


public class MediaSessionTextHandler extends BaseSignalHandler {
    private MediaSessionManager mediaSessionManager;
    private MediaSession mediaSession;

    private static final MediaSessionTextHandler ourInstance = new MediaSessionTextHandler();

    public static MediaSessionTextHandler getInstance() {
        return ourInstance;
    }

    public MediaSessionTextHandler setContext(Context ctx) {
        context = ctx;
        mediaSessionManager = (MediaSessionManager) context.getSystemService(Context.MEDIA_SESSION_SERVICE);
        mediaSession = new MediaSession(context, "CarPlayer");
        mediaSession.setActive(true);
        mediaSession.setFlags(MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Attach Callback to receive MediaSession updates
        mediaSession.setCallback(new MediaSession.Callback() {
            // Implement callbacks
            @Override
            public void onPlay() {
                super.onPlay();
                Toast.makeText(context, "onPlay()", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPause() {
                super.onPause();
                Toast.makeText(context, "onPause()", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSkipToNext() {
                super.onSkipToNext();
                Toast.makeText(context, "onSkipToNext", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSkipToPrevious() {
                super.onSkipToPrevious();
                Toast.makeText(context, "onSkipTopPrevious", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStop() {
                super.onStop();
                Toast.makeText(context, "onStop", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSeekTo(long position) {
                super.onSeekTo(position);
                Toast.makeText(context, "onSeekTo()", Toast.LENGTH_SHORT).show();
            }
        });

        
        return this;
    }

    @Override
    public String getMessageId() {
        return "a4";
    }

    @Override
    public String getSignalName() {
        return "text";
    }

    @Override
    public void handle(Signal signal, Bus bus) {

    }

    public void updateMetaData(String title, String artist, String album) {
        Bitmap albumArt = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.audio_icon); //replace with medias albumArt
        // Update the current metadata
        mediaSession.setMetadata(new MediaMetadata.Builder()
                .putBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART, albumArt)
                .putString(MediaMetadata.METADATA_KEY_ARTIST, artist)
                .putString(MediaMetadata.METADATA_KEY_ALBUM, album)
                .putString(MediaMetadata.METADATA_KEY_TITLE, title)
                .build());
    }
}
