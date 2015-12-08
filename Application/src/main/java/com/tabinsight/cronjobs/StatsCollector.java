package com.tabinsight.cronjobs;

import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.tabinsight.constants.LogTags;
import com.tabinsight.constants.WhitelistedApps;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.tabinsight.database.AppsInfoDatasource;

public class StatsCollector extends Service {
    public StatsCollector() {
    }
    UsageStatsManager mUsageStatsManager;
    AppsInfoDatasource appsInfoDatasource;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        Log.d(LogTags.APP_INFO.name(), "Service started");

        appsInfoDatasource = new AppsInfoDatasource(getBaseContext());
        mUsageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        // TODO: Return the communication channel to the service.
        HashMap<String, UsageStats> usageStatsMap = getUsageStatsMap();
        WhitelistedApps whiteListedApps = new WhitelistedApps();
        List<String> packageNames= whiteListedApps.getPackageNames();
        List<String> log = new ArrayList<>();
        try {
            appsInfoDatasource.open();
            for (String name : packageNames) {
                if (!usageStatsMap.containsKey(name)) {
                    continue;
                }
                UsageStats usageStat = usageStatsMap.get(name);
                Log.d(LogTags.APP_DEBUG.name(), "In StatsCollector: "+usageStat.getLastTimeUsed() + "\tdummydevice\t" + usageStat.getPackageName() + "\t" + (usageStat.getTotalTimeInForeground()/1000));
                appsInfoDatasource.createEntry(usageStat.getPackageName(), usageStat.getLastTimeUsed() + "", usageStat.getTotalTimeInForeground());
            }
        }
        catch(SQLException e){
            Log.d(LogTags.APP_EXCEPTION.name(), "SQLException");
        }
        Log.d(LogTags.APP_INFO.name(), "Service stopped");
        stopSelf();
    }

    @Override
    public void onDestroy(){
        appsInfoDatasource.close();
    }

    private HashMap<String, UsageStats> getUsageStatsMap(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);

        List<UsageStats> queryUsageStats = mUsageStatsManager
                .queryUsageStats(0, cal.getTimeInMillis(),
                        System.currentTimeMillis());
        HashMap<String, UsageStats> usageStatsMap = new HashMap<>();
        for(UsageStats qsm: queryUsageStats){
            usageStatsMap.put(qsm.getPackageName(),qsm);
        }
        return usageStatsMap;
    }
}
