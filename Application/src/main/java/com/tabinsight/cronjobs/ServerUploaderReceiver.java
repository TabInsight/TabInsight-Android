package com.tabinsight.cronjobs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.tabinsight.constants.LogTags;
import com.tabinsight.util.TabInsightApplicationContext;

public class ServerUploaderReceiver extends BroadcastReceiver {

    private static final long WIFI_STABILISATION_TIME = 30000;

    public ServerUploaderReceiver() {
    }

    private void sleep(long waitTime){
        try {
            Thread.sleep(waitTime);
        } catch (Exception e) {
            Log.d(LogTags.APP_DEBUG.name(), "delay failed!");
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(LogTags.APP_INFO.name(), "ServerUploaderReceiver starting");
        String client = null;
        if(TabInsightApplicationContext.getBroadcastRecieverClient()!=null){
            client = TabInsightApplicationContext.getBroadcastRecieverClient().name();
        }

        final String action = intent.getAction();

        if(!TabInsightApplicationContext.BroadcastRecieverClients.UI_ACTIVITY.equals(TabInsightApplicationContext.getBroadcastRecieverClient())){
            sleep(WIFI_STABILISATION_TIME);
            TabInsightApplicationContext.setBroadcastRecieverClient(TabInsightApplicationContext.BroadcastRecieverClients.SERVICE);
        }

        if (action != null) {
            WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            Log.d(LogTags.APP_DEBUG.name(), "wifi has value "+ wifi.getWifiState());
            if (wifi.getWifiState() == WifiManager.WIFI_STATE_ENABLED ) {
                Log.d(LogTags.APP_DEBUG.name(), "WIFI enabled");
                context.startService(new Intent(context, ServerUploader.class));
            }

        } else {

            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (mWifi.isConnected()) {
                context.startService(new Intent(context, ServerUploader.class));
            }

            Log.d(LogTags.APP_INFO.name(), "ServerUploaderReceiver completed");
        }
    }
}
