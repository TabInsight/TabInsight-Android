package com.tabinsight.cronjobs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tabinsights.constants.LogTags;

public class StatsCollectionAlarmReceiver extends BroadcastReceiver {
    public StatsCollectionAlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LogTags.APP_INFO.name(),"Alarm recieved in StatsCollectionAlarmReceiver");
        context.startService(new Intent(context, StatsCollector.class));
    }
}
