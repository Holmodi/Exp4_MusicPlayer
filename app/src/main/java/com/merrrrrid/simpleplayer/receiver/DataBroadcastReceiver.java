package com.merrrrrid.simpleplayer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.merrrrrid.simpleplayer.service.PlayerService;

public class DataBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null) {
            switch (action) {
                case "pause": {
                    Bundle bundle = new Bundle();
                    bundle.putString("action", "pause");
                    Intent serviceIntent = new Intent(context, PlayerService.class);
                    serviceIntent.putExtras(bundle);
                    context.startService(serviceIntent);
                    break;
                }
                case "play": {
                    Bundle bundle = new Bundle();
                    bundle.putString("action", "play");
                    Intent serviceIntent = new Intent(context, PlayerService.class);
                    serviceIntent.putExtras(bundle);
                    context.startService(serviceIntent);
                    break;
                }
                case "stop": {
                    Bundle bundle = new Bundle();
                    bundle.putString("action", "stop");
                    Intent serviceIntent = new Intent(context, PlayerService.class);
                    serviceIntent.putExtras(bundle);
                    context.startService(serviceIntent);
                    break;
                }
                case "first_start": {
                    Bundle bundle = new Bundle();
                    bundle.putString("action", "first_start");
                    Intent serviceIntent = new Intent(context, PlayerService.class);
                    serviceIntent.putExtras(bundle);
                    context.startService(serviceIntent);
                    break;
                }
                default:
                    break;
            }
        }
    }
}
