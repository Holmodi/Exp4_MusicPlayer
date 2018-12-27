package com.merrrrrid.simpleplayer.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.merrrrrid.simpleplayer.R;
import com.merrrrrid.simpleplayer.activity.MainActivity;
import com.merrrrrid.simpleplayer.utils.Utils;

public class MusicControlNotification {

    private Context context;
    private String channelId = "player_channel_id";
    private int notificationId = 666;
    private RemoteViews expandedView;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builderNC;

    public MusicControlNotification(Context context) {
        this.context = context;
    }

    public void createControlBarNotification() {

        expandedView = new RemoteViews(context.getPackageName(), R.layout.control_bar);
        builderNC = new NotificationCompat.Builder(context, channelId);

        builderNC.setCustomBigContentView(expandedView)
                .setChannelId(channelId)
                .setContentTitle(MainActivity.artistName)
                .setContentText(MainActivity.trackName)
                .setSmallIcon(R.drawable.ic_audiotrack_black_24dp)
                .setAutoCancel(true);

        notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(channelId, "player_channel", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        expandedView.setOnClickPendingIntent(R.id.resume_pause_button_iv, Utils.getPendingIntent("pause", context));
        expandedView.setOnClickPendingIntent(R.id.close_button_iv, Utils.getPendingIntent("stop", context));
        expandedView.setTextViewText(R.id.author_tv, MainActivity.artistName);
        expandedView.setTextViewText(R.id.track_name_tv, MainActivity.trackName);

        notificationManager.notify(notificationId, builderNC.build());
    }

    public void initPauseClick() {
        expandedView.setImageViewResource(R.id.resume_pause_button_iv, R.drawable.ic_play_arrow_black_24dp);
        expandedView.setOnClickPendingIntent(R.id.resume_pause_button_iv, Utils.getPendingIntent("play", context));
        notificationManager.notify(notificationId, builderNC.build());
        MainActivity.initPause(context);
    }

    public void initResumeClick() {
        expandedView.setImageViewResource(R.id.resume_pause_button_iv, R.drawable.ic_pause_black_24dp);
        expandedView.setOnClickPendingIntent(R.id.resume_pause_button_iv, Utils.getPendingIntent("pause", context));
        notificationManager.notify(notificationId, builderNC.build());
        MainActivity.initResume(context);
    }

    public void initStopClick() {
        MainActivity.clearView();
        Utils.clearNotificationBar(context);
    }

}
