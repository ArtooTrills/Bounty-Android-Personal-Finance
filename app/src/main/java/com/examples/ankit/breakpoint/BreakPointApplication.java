package com.examples.ankit.breakpoint;

import android.app.Application;
import android.content.Context;

/**
 * Created by ankit on 20/05/17.
 */

public class BreakPointApplication extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    //Returns the application context
    public static Context getAppContext() {
        return mContext;
    }

}
