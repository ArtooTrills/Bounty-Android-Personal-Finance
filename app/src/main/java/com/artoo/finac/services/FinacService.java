package com.artoo.finac.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class FinacService extends Service{

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
