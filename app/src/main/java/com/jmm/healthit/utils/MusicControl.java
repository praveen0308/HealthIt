package com.jmm.healthit.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;

import com.jmm.healthit.R;

public class MusicControl {
    private static MusicControl sInstance;
    private Context mContext;
    private MediaPlayer mMediaPlayer;

    public MusicControl(Context context) {
        mContext = context;
    }

    public static MusicControl getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new MusicControl(context);
        }
        return sInstance;
    }

    public void playMusic() {
        try {
            /*mMediaPlayer = MediaPlayer.create(context, R.raw.music);
            mMediaPlayer.start();*/
            Uri ringtoneAlert =  RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(mContext, ringtoneAlert);

            final AudioManager audioRingtoneManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);

            if (audioRingtoneManager.getStreamVolume(AudioManager.STREAM_RING) != 0) {

                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
                mMediaPlayer.setLooping(true);
                mMediaPlayer.prepare();
                mMediaPlayer.start();

                RingtoneStopper stopper = new RingtoneStopper(10000,1000);
                stopper.start();
            }
        } catch(Exception e) {

            //do some message to user if some error occurs
        }
    }

    public void stopMusic() {
        if(mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.seekTo(0);
        }
    }

    public class RingtoneStopper extends CountDownTimer {

        public RingtoneStopper(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            mMediaPlayer.stop();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //need nothing to do on tick events
        }
    }
}
