package com.artoo.finac.services;

import android.content.Context;

public class HandleMessages {

    private static HandleMessages instance;
    private static Context context;

    //  Singleton Implementation
    private HandleMessages() {
    }

    public static HandleMessages getInstance(Context c) {

        context = c;

        if (instance == null)
            instance = new HandleMessages();

        return instance;
    }

    public void doReadRecentMesasges() {


    }
}
