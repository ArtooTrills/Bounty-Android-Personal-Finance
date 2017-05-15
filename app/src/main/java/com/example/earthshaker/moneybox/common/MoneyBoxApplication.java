package com.example.earthshaker.moneybox.common;

import android.app.Application;

import com.example.earthshaker.moneybox.common.dao.db.DatabaseProvider;

/**
 * Created by earthshaker on 14/5/17.
 */

public class MoneyBoxApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseProvider.initializeDatabase(this);
    }
}
