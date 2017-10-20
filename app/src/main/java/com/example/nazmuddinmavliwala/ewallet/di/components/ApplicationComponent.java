package com.example.nazmuddinmavliwala.ewallet.di.components;

import android.app.Application;
import android.content.Context;

import com.example.nazmuddinmavliwala.ewallet.data.disc.ApplicationContext;
import com.example.nazmuddinmavliwala.ewallet.di.identifiers.ActivityContext;
import com.example.nazmuddinmavliwala.ewallet.di.modules.ApplicationModule;
import com.example.nazmuddinmavliwala.ewallet.di.modules.DataModule;
import com.google.gson.Gson;

import java.io.File;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

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

    @ApplicationContext
    Context getApplicationContext();

    Application getApplication();


}
