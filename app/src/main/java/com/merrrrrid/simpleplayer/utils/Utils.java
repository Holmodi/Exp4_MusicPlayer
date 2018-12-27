package com.merrrrrid.simpleplayer.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.merrrrrid.simpleplayer.receiver.DataBroadcastReceiver;

public class Utils {

    public static PendingIntent getPendingIntent(String action, Context context) {
        Intent broadcastReceiverIntent = new Intent(context, DataBroadcastReceiver.class);
        broadcastReceiverIntent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, broadcastReceiverIntent, 0);
    }

    public static void sendPendingIntent(String action, Context context) {
        try {
            getPendingIntent(action, context).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    public static void clearNotificationBar(Context context) {
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

}
