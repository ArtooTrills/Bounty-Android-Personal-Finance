package com.example.nazmuddinmavliwala.ewallet.data.database;

import android.content.Context;


/**
 * Created by nazmuddinmavliwala on 12/01/17.
 */
public class DatabaseManager {

    private static DatabaseManager instance;
    public static final Boolean ENCRYPTED = true;
//    private final DaoSession daoSession;

    private DatabaseManager(Context context) {
        WalletDatabase database = WalletDatabase.getInstance(context);
//        DaoMaster daoMaster = new DaoMaster(database.getWritableDatabase());
//        DaoMaster.createAllTables(daoMaster.getDatabase(), ENCRYPTED);
//        daoSession = daoMaster.newSession();
    }

    public static DatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManager(context);
        }
        return instance;
    }

//    public DaoSession getDaoSession() {
//        return daoSession;
//    }
}
