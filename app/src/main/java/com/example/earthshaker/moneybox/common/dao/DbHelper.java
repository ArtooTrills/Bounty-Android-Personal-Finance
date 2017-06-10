package com.example.earthshaker.moneybox.common.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.earthshaker.moneybox.common.dao.db.Contract;

import static com.example.earthshaker.moneybox.common.dao.db.Schema.SQL_CREATE_BUDGET;
import static com.example.earthshaker.moneybox.common.dao.db.Schema.SQL_CREATE_DATABASE_VERION_TABLE;
import static com.example.earthshaker.moneybox.common.dao.db.Schema.SQL_CREATE_TRANSACTIONS;

/**
 * Created by earthshaker on 14/5/17.
 */

public class DbHelper extends SQLiteOpenHelper {

    private Context context;

    public DbHelper(Context context, String databaseName, int databaseVersion) {
        super(context, databaseName, null, databaseVersion);
        this.context = context;
    }

    @Override public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_BUDGET);
        db.execSQL(SQL_CREATE_TRANSACTIONS);
        db.execSQL(SQL_CREATE_DATABASE_VERION_TABLE);
        ContentValues cv = new ContentValues();

        cv.put(Contract.DatabaseVersion.VERSION, 0);
        db.insert(Contract.DatabaseVersion.TABLE_NAME, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}