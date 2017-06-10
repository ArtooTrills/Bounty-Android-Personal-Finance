package com.example.earthshaker.moneybox.common.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.earthshaker.moneybox.common.callback.BaseCallback;
import com.example.earthshaker.moneybox.common.callback.ParameterCallback;
import com.example.earthshaker.moneybox.common.dao.db.DatabaseProvider;

/**
 * Created by earthshaker on 14/5/17.
 */

public class BaseDao {

    public static final String TAG = BaseDao.class.getSimpleName();
    protected static CursorHelper cursorHelper;


    public static void runRawQuery(String query, ParameterCallback<Cursor> callback) {
        try {
            Cursor c = DatabaseProvider.provideDatabase().rawQuery(query, null);
            CursorHelper.iterateCursor(c, callback);
        } catch (IllegalStateException e) {
            Log.e(TAG, e.toString());
        }
    }

    public static <T> T getFirstRecord(String query, BaseCallback<T, Cursor> callback) {
        try {
            Cursor cursor = DatabaseProvider.provideDatabase().rawQuery(query, null);
            return cursorHelper.getFirstEntry(cursor, callback);
        } catch (IllegalStateException e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

}