package com.tabinsight.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.tabinsight.constants.LogTags;
import com.tabinsight.cronjobs.ServerUploaderReceiver;
import com.tabinsight.cronjobs.StatsCollectionAlarmReceiver;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by bijilap on 11/26/15.
 */
public class SetAlarmHelper {

    public static void wifiPresentAlarm(Context context){

        Log.d(LogTags.APP_DEBUG.name(), "Creating wifi present alarm");

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
        ServerUploaderReceiver serverUploaderReceiver = new ServerUploaderReceiver();
        context.registerReceiver(serverUploaderReceiver, intentFilter);
    }

    public static void setRecurringAlarm(Context context) {

        Calendar updateTime = Calendar.getInstance();
        updateTime.setTimeZone(TimeZone.getTimeZone("GMT"));
        updateTime.set(Calendar.HOUR_OF_DAY, 10);
        updateTime.set(Calendar.MINUTE, 30);

        Intent downloader = new Intent(context, StatsCollectionAlarmReceiver.class);
        PendingIntent statCollector = PendingIntent.getBroadcast(context,
                0, downloader, 0);
        AlarmManager alarms = (AlarmManager) context.getSystemService(
                Context.ALARM_SERVICE);

        alarms.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                0,
                AlarmManager.INTERVAL_HALF_DAY, statCollector);

    }


}
