package com.example.nazmuddinmavliwala.ewallet.di.modules;

import android.app.Application;
import android.content.Context;

import com.example.nazmuddinmavliwala.ewallet.data.disc.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nazmuddinmavliwala on 22/01/17.
 */


@Module
@Singleton
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return this.application;
    }

    @Provides
    @Singleton
    @ApplicationContext
    public Context provideApplicationContext() {
        return this.application;
    }


}
