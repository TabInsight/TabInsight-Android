package com.tabinsight.cronjobs;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.android.appusagestatistics.R;
import com.tabinsights.constants.LogTags;
import com.tabinsights.rest.EndPoints;
import com.tabinsights.rest.RestClient;

import java.util.List;

import tabinsights.database.AppUseInfo;
import tabinsights.database.AppsInfoDatasource;

public class ServerUploader extends Service {
    public ServerUploader() {
    }

    AppsInfoDatasource appsInfoDatasource;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {

        appsInfoDatasource = new AppsInfoDatasource(getBaseContext());

        String []params = new String[6];
        params[0] = RestClient.HTTPMethod.POST.name();
        params[1] = "http://"+getString(R.string.server_ip_address)+ EndPoints.PUBLISH_DEVICE_LOGS;
        Log.d(LogTags.APP_INFO.name(), params[1]);

        try {
            appsInfoDatasource.open();
            List<AppUseInfo> records = appsInfoDatasource.getAllRecords();

            Log.d(LogTags.APP_INFO.name(), "Pushing data to server -- start");
            for(AppUseInfo record: records){
                RestClient rc = new RestClient();
                params[2] = "device="+"ugfte6t83wfgiwfgsigfvgsi";
                params[3] = "access_time="+record.access_time;
                params[4] = "app_name="+record.app_name;
                params[5] = "use_time="+record.use_time;
                rc.execute(params);
            }

            appsInfoDatasource.truncateTable();
            Log.d(LogTags.APP_INFO.name(), "Pushing data to server -- end");

        }
        catch(Exception e){

        }
        stopSelf();
    }


    @Override
    public void onDestroy(){
        appsInfoDatasource.close();
    }
}
