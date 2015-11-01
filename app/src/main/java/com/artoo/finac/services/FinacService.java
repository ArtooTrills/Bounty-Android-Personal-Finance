package com.artoo.finac.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

/* Uses */
/* To keep on syncing the data in the server */

public class FinacService extends Service {

    //  DM
    private final static String TAG = "Finac Service";

    //  FN

    public FinacService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //  Instructions for Backup service.
        //  LATER

        //  CONDITIONS FROM PREFERENCE LEFT
        SyncProfile syncProfile = SyncProfile.getInstance(getApplicationContext());
        syncProfile.doSyncProfile();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
