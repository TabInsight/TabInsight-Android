package com.tabinsight.cronjobs;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.tabinsight.usage.satistics.R;
import com.tabinsight.system.internals.PhoneDetailHelper;
import com.tabinsight.constants.LogTags;
import com.tabinsight.rest.EndPoints;
import com.tabinsight.rest.RestClient;

import java.util.List;

import com.tabinsight.database.AppUseInfo;
import com.tabinsight.database.AppsInfoDatasource;

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

        Log.d(LogTags.APP_DEBUG.name(), "Inside create of server uploader");

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

                PhoneDetailHelper phoneDetailHelper = new PhoneDetailHelper();
                String deviceId = phoneDetailHelper.getUniqueId(this);

                String []newparams = new String[6];
                newparams[0] = params[0];
                newparams[1] = params[1];
                newparams[2] = "device=" + deviceId;
                newparams[3] = "access_time=" + record.access_time;
                newparams[4] = "app_name=" + record.app_name;
                newparams[5] = "use_time=" + record.use_time;

                Log.d(LogTags.APP_DEBUG.name(), "uploader:"+record.app_name);

                rc.execute(newparams);

                appsInfoDatasource.deleteEntry(record.app_name, record.access_time);
            }

            appsInfoDatasource.truncateTable();
            Log.d(LogTags.APP_INFO.name(), "Pushing data to server -- end");

        }
        catch(Exception e){
            e.printStackTrace();
        }
        stopSelf();
    }


    @Override
    public void onDestroy(){
        appsInfoDatasource.close();
    }
}
