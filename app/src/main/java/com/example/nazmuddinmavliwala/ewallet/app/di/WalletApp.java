package com.example.nazmuddinmavliwala.ewallet.app.di;

import android.app.Application;

import com.example.nazmuddinmavliwala.ewallet.app.di.components.ApplicationComponent;
import com.example.nazmuddinmavliwala.ewallet.app.di.components.DaggerApplicationComponent;
import com.example.nazmuddinmavliwala.ewallet.app.di.modules.ApplicationModule;
import com.example.nazmuddinmavliwala.ewallet.app.di.modules.DataModule;

/**
 * Created by nazmuddinmavliwala on 01/11/2017.
 */

public class WalletApp extends Application {

    private static ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .dataModule(new DataModule())
                .build();
    }

    public static ApplicationComponent getComponent() {
        return component;
    }
}
