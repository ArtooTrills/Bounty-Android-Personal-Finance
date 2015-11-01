package com.artoo.finac.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

/* Uses */
/* To Receive Messages, update the database and then broadcast the transaction update Messages */
/* To keep on syncing the data in the server */

public class FinacService extends Service {

    //  DM
    private final static String TAG = "Finac Service";

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //  Do the Handle Message part.
            Log.d(TAG, "Reading Messages!");
            HandleMessages handleMessages = HandleMessages.getInstance(getApplicationContext());
            handleMessages.doReadRecentMesasges();
        }
    };

    //  FN

    public FinacService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter();

        registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //  Instructions for Backup service.
        //  LATER
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
