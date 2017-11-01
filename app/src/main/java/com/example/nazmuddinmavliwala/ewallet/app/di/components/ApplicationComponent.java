package com.example.nazmuddinmavliwala.ewallet.app.di.components;

import android.app.Application;
import android.content.Context;
import com.example.nazmuddinmavliwala.ewallet.app.di.modules.ApplicationModule;
import com.example.nazmuddinmavliwala.ewallet.app.di.modules.DataModule;
import com.nazmuddinmavliwala.ewallet.database.entities.DaoSession;
import com.nazmuddinmavliwala.ewallet.network.NetworkService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by nazmuddinmavliwala on 22/01/17.
 */

@Singleton
@Component(
        modules = {
                ApplicationModule.class,
                DataModule.class
        }
)
public interface ApplicationComponent {

    @Named("Application")
    Context getApplicationContext();

    Application getApplication();

    DaoSession provideDaoSession();

    NetworkService provideNetworkService();


}
