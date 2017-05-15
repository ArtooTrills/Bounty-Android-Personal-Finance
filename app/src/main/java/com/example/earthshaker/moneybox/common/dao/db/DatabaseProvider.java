package com.example.earthshaker.moneybox.common.dao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.earthshaker.moneybox.common.dao.DbHelper;

/**
 * Created by earthshaker on 14/5/17.
 */

public class DatabaseProvider {

    public static SQLiteDatabase  database;
    public static void initializeDatabase(Context c){
        database = new DbHelper(c,"contract.db",1).getWritableDatabase();
        database.enableWriteAheadLogging();
    }

    public static SQLiteDatabase provideDatabase(){
        return database;
    }


}
