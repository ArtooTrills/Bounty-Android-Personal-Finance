package com.nazmuddinmavliwala.ewallet.database;

import android.content.Context;

import com.nazmuddinmavliwala.ewallet.database.entities.DaoMaster;
import com.nazmuddinmavliwala.ewallet.database.entities.DaoSession;

import org.greenrobot.greendao.database.Database;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Created by nazmuddinmavliwala on 29/10/2017.
 */

@Singleton
public class DatabaseManager {

    private static DatabaseManager instance;
    private final DaoSession daoSession;

    @Inject
    public DatabaseManager(@Named("Application") Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context,"turvo-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }


    public static DatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManager(context);
        }
        return instance;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

}
