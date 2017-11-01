package com.example.nazmuddinmavliwala.ewallet.app.di.modules;

import android.app.Application;
import android.content.Context;

import javax.inject.Named;
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
    @Named("Application")
    public Context provideApplicationContext() {
        return this.application;
    }


}
