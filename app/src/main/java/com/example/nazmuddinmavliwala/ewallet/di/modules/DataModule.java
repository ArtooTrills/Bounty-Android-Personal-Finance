package com.example.nazmuddinmavliwala.ewallet.di.modules;

import android.content.ContentResolver;
import android.content.Context;
import android.text.format.DateUtils;

import com.example.nazmuddinmavliwala.ewallet.data.database.DatabaseManager;
import com.example.nazmuddinmavliwala.ewallet.data.disc.ApplicationContext;
import com.example.nazmuddinmavliwala.ewallet.data.disc.SharedPrefManager;
import com.example.nazmuddinmavliwala.ewallet.data.disc.SharedPrefService;
import com.example.nazmuddinmavliwala.ewallet.domain.Auth;
import com.example.nazmuddinmavliwala.ewallet.domain.AuthManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by nazmuddinmavliwala on 22/01/17.
 */


@Module
@Singleton
public class DataModule {

    private static final String API_BASE_URL = "http:api3.aasaanjobs.com/";
    //private static final String API_BASE_URL = "https:api.aasaanjobs.com/";
    public static final String WEB_URL = "https://www.aasaanjobs.com/";

    @Singleton
    @Provides
    public ContentResolver provideResolver(@ApplicationContext Context context) {
        return context.getContentResolver();
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder()
                .create();
    }


    @Provides
    @Singleton
    public DatabaseManager provideDBManager(@ApplicationContext Context context) {
        return DatabaseManager.getInstance(context);
    }



    @Provides
    @Singleton
    public Interceptor provideInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(Interceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    @Singleton
    public SharedPrefService provideSharedPrefService(@ApplicationContext Context context) {
        return new SharedPrefManager(context);
    }

    @Provides
    @Singleton
    public Auth provideAuth(AuthManager authManager) {
        return authManager;
    }

    @Provides
    @Singleton
    public File provideCacheDir(@ApplicationContext Context context) {
        return context.getCacheDir();
    }


}
