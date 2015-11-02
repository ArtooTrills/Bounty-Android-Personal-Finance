package com.artoo.finac.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class StartServicesAtStartup extends BroadcastReceiver {

    private final static String TAG = "Finac Startup";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {

            //
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
            boolean readMessages = settings.getBoolean("syncService",false);

            if (readMessages) {

                Intent serviceIntent = new Intent(context, FinacService.class);
                context.startService(serviceIntent);
                Log.d(TAG, "Finac Services started!!!");
            }
        }
    }

}