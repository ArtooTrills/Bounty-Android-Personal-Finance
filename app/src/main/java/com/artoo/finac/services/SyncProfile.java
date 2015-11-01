package com.artoo.finac.services;

import android.content.Context;

public class SyncProfile {

    private static Context context;
    private static SyncProfile instance;

    //  Singleton Implementation
    private SyncProfile() {
    }

    public static SyncProfile getInstance(Context c) {

        context = c;

        if (instance == null)
            instance = new SyncProfile();

        return instance;
    }

    public void doSyncProfile() {


    }
}
