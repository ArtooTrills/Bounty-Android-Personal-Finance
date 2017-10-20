package com.example.nazmuddinmavliwala.ewallet.data.database;

import android.content.Context;


/**
 * Created by nazmuddinmavliwala on 11/12/15.
 */
public class WalletDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = Config.DATABASE_NAME;
    private static final int DATABASE_VERSION = Config.DATABASE_VERSION;
    private static WalletDatabase instance;

    public static WalletDatabase getInstance(Context context) {
        if(instance == null) {
            instance = new WalletDatabase(context);
        }
        return instance;
    }

    private WalletDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
    }
}
