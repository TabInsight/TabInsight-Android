package com.tabinsight.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

/**
 * DataUploaderService is an intent service (background service) that uploads data to the server
 * when wifi connection is present
 *
 */
public class DataUploaderService extends IntentService {


    public DataUploaderService() {
        super("DataUploaderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
