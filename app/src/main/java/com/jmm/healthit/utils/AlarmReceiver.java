package com.jmm.healthit.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.jmm.healthit.R;
import com.jmm.healthit.ui.dashboard.SetReminder;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    private MediaPlayer mRingtoneLooper;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Testing","ok");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context , default_notification_channel_id ) ;
        builder.setContentTitle( "This is alarm" );

       /* String action = intent.getAction() ;
        Log. e ( "USB" , action.toString()) ;*/
//        assert action != null;
        builder.setContentText( "Your time is to drink a glass of water." ) ;
        builder.setSmallIcon(R.drawable. ic_logo ) ;
        builder.setAutoCancel( true ) ;
        Intent intent1 = new Intent(context, SetReminder.class);

        PendingIntent pIntent = PendingIntent.getActivity(context, 0,
                intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pIntent);
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        Notification notification = builder.build() ;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE ) ;

        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager.IMPORTANCE_LOW ;
            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert notificationManager != null;

        notificationManager.notify( 1 , notification) ;

        /*Uri ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(context, ringtone);
        r.play();*/
        /*if ( connected ) {

            connected = false;
        } else {
            notificationManager.cancel( 1 ) ;
            connected = true;
        }*/

        try {

            Uri ringtoneAlert =  RingtoneManager.
                    getDefaultUri(RingtoneManager.TYPE_ALARM);

            mRingtoneLooper = new MediaPlayer();
            mRingtoneLooper.setDataSource(context, ringtoneAlert);

            final AudioManager audioRingtoneManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

            if (audioRingtoneManager.getStreamVolume(AudioManager.STREAM_RING) != 0) {

                mRingtoneLooper.setAudioStreamType(AudioManager.STREAM_RING);
                mRingtoneLooper.setLooping(true);
                mRingtoneLooper.prepare();
                mRingtoneLooper.start();

                //start custom countdown timer for 60 seconds, counts every second down
                //counting every second down is not necessary, You could even set every 5 seconds or whatever You want

                RingtoneStopper stopper = new RingtoneStopper(60000,1000);
                stopper.start();
            }
        } catch(Exception e) {

            //do some message to user if some error occurs
        }
    }

    public class RingtoneStopper extends CountDownTimer {

        public RingtoneStopper(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            mRingtoneLooper.stop();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //need nothing to do on tick events
        }
    }
}