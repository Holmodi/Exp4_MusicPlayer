package com.merrrrrid.simpleplayer.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;

import com.merrrrrid.simpleplayer.activity.MainActivity;
import com.merrrrrid.simpleplayer.notification.MusicControlNotification;
import com.merrrrrid.simpleplayer.receiver.DataBroadcastReceiver;

import java.io.IOException;

public class PlayerService extends Service implements MediaPlayer.OnCompletionListener {

    MusicControlNotification musicControlNotification;
    MediaPlayer mMediaPlayer = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            Message msg = new Message();
            msg.setData(bundle);
            ServiceHandler mServiceHandler = new ServiceHandler(getMainLooper());
            mServiceHandler.sendMessage(msg);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Intent broadcastReceiverIntent = new Intent(this, DataBroadcastReceiver.class);
        broadcastReceiverIntent.setAction("stop");
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, broadcastReceiverIntent, 0);
        try {
            pi.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        if (mMediaPlayer != null)
            mMediaPlayer.pause();
        MainActivity.isPlaying = false;

    }

    public void play() {
        if (mMediaPlayer != null)
            mMediaPlayer.start();
        MainActivity.isPlaying = true;
    }

    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
            MainActivity.trackURI = null;
            MainActivity.isPlaying = false;
        }
    }

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String action = bundle.getString("action");
            if (action != null) {
                switch (action) {
                    case "stop":
                        musicControlNotification.initStopClick();
                        stop();
                        break;
                    case "pause":
                        musicControlNotification.initPauseClick();
                        pause();
                        break;
                    case "play":
                        musicControlNotification.initResumeClick();
                        play();
                        break;
                    case "first_start":
                        stop();
                        MainActivity.isPlaying = true;

                        musicControlNotification = new MusicControlNotification(getApplicationContext());
                        musicControlNotification.createControlBarNotification();

                        try {
                            mMediaPlayer = new MediaPlayer();
                            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            mMediaPlayer.setDataSource(getApplicationContext(), MainActivity.trackURI);
                            mMediaPlayer.prepareAsync();
                            mMediaPlayer.setOnPreparedListener(MediaPlayer::start);
                        } catch (IOException e) {
                            e.printStackTrace();
                            stop();
                        }
                        break;
                }
            }
        }
    }
}
