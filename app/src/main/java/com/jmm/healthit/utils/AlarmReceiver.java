package com.jmm.healthit.utils;

import static com.jmm.healthit.utils.Constants.ALARM_NOTIFICATION_ID;

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
        MusicControl.getInstance(context).playMusic();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context , default_notification_channel_id ) ;
        builder.setContentTitle( "This is alarm" );

        builder.setContentText( "Your time is to drink a glass of water." ) ;
        builder.setSmallIcon(R.drawable. ic_logo ) ;
        builder.setAutoCancel( true ) ;
        Intent intent1 = new Intent(context, SetReminder.class);

        PendingIntent pIntent = PendingIntent.getActivity(context, 0,
                intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent dismissIntent = new Intent(context,DismissBroadcast.class);
        PendingIntent piDismiss = PendingIntent.getBroadcast(context, 100, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        builder.setContentIntent(pIntent);
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        builder.addAction(R.drawable.ic_logo,
                "Dismiss", piDismiss);
        Notification notification = builder.build() ;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE ) ;

        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager.IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert notificationManager != null;

        notificationManager.notify( ALARM_NOTIFICATION_ID , notification) ;

    }


}