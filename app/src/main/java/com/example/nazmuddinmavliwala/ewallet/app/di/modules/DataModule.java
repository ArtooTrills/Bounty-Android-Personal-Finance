package com.example.nazmuddinmavliwala.ewallet.app.di.modules;

import com.google.gson.Gson;
import com.nazmuddinmavliwala.ewallet.database.DatabaseManager;
import com.nazmuddinmavliwala.ewallet.database.entities.DaoSession;
import com.nazmuddinmavliwala.ewallet.network.NetworkManager;
import com.nazmuddinmavliwala.ewallet.network.NetworkService;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


/**
 * Created by nazmuddinmavliwala on 22/01/17.
 */


@Module
@Singleton
public class DataModule {
    @Singleton
    @Provides
    public Gson provideGson() {
        return new Gson();
    }

    @Singleton
    @Provides
    public NetworkService provideNetworkService(NetworkManager manager) {
        return manager;
    }

    @Singleton
    @Provides
    public DaoSession provideDaoSession(DatabaseManager manager) {
        return manager.getDaoSession();
    }
}
