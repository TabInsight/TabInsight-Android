package com.tabinsight.cronjobs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.tabinsights.constants.LogTags;

public class ServerUploaderReceiver extends BroadcastReceiver {
    public ServerUploaderReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        Log.d(LogTags.APP_INFO.name(),"ServerUploaderReceiver starting");
        if(action != null){
            Log.d(LogTags.APP_INFO.name(), "action is null");
            if (action.equals(WifiManager.WIFI_STATE_ENABLED)) {
                context.startService(new Intent(context, ServerUploader.class));
            }
        }

        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            context.startService(new Intent(context, ServerUploader.class));
        }

        Log.d(LogTags.APP_INFO.name(),"ServerUploaderReceiver completed");
    }
}
