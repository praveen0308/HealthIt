package com.jmm.healthit.utils;

import static com.jmm.healthit.utils.Constants.ALARM_NOTIFICATION_ID;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DismissBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MusicControl.getInstance(context).stopMusic();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE );
        notificationManager.cancel(ALARM_NOTIFICATION_ID);
    }
}