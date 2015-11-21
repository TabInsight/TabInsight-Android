/*
* Copyright 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.appusagestatistics;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;

import com.tabinsight.cronjobs.ServerUploaderReceiver;
import com.tabinsight.cronjobs.StatsCollectionAlarmReceiver;
import com.tabinsight.cronjobs.StatsCollector;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Launcher Activity for the App Usage Statistics sample app.
 */
public class AppUsageStatisticsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRecurringAlarm(this);
        wifiPresentAlarm(this);
        setContentView(R.layout.activity_app_usage_statistics);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, AppUsageStatisticsFragment.newInstance())
                    .commit();
        }

    }

    private void setRecurringAlarm(Context context) {

        // we know mobiletuts updates at right around 1130 GMT.
        // let's grab new stuff at around 11:45 GMT, inexactly
        Calendar updateTime = Calendar.getInstance();
        updateTime.setTimeZone(TimeZone.getTimeZone("GMT"));
        updateTime.set(Calendar.HOUR_OF_DAY, 10);
        updateTime.set(Calendar.MINUTE, 30);

        Intent downloader = new Intent(context, StatsCollectionAlarmReceiver.class);
        PendingIntent statCollector = PendingIntent.getBroadcast(context,
                0, downloader, 0);
        AlarmManager alarms = (AlarmManager) getSystemService(
                Context.ALARM_SERVICE);

        alarms.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                0,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES, statCollector);

        /*
        alarms.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(),
                60000, statCollector);
                */
    }

    private void wifiPresentAlarm(Context context){
        /*
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
        ServerUploaderReceiver serverUploaderReceiver = new ServerUploaderReceiver();
        registerReceiver(serverUploaderReceiver, intentFilter);
        */
        Calendar updateTime = Calendar.getInstance();
        updateTime.setTimeZone(TimeZone.getTimeZone("GMT"));
        updateTime.set(Calendar.HOUR_OF_DAY, 10);
        updateTime.set(Calendar.MINUTE, 30);

        Intent downloader = new Intent(context, ServerUploaderReceiver.class);
        PendingIntent statCollector = PendingIntent.getBroadcast(context,
                0, downloader, 0);
        AlarmManager alarms = (AlarmManager) getSystemService(
                Context.ALARM_SERVICE);

        alarms.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                0,
                AlarmManager.INTERVAL_HOUR, statCollector);
    }
}