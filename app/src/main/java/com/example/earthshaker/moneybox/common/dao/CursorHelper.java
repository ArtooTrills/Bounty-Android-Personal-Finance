package com.example.earthshaker.moneybox.common.dao;

import android.database.Cursor;
import android.util.Log;

import com.example.earthshaker.moneybox.common.callback.BaseCallback;
import com.example.earthshaker.moneybox.common.callback.ParameterCallback;
import com.example.earthshaker.moneybox.common.callback.ReturnWithParameterCallback;
import com.example.earthshaker.moneybox.dashboard.activity.DashboardActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by earthshaker on 14/5/17.
 */
public class CursorHelper {

    public static final String TAG = CursorHelper.class.getSimpleName();

    public static void iterateCursor(Cursor c) {
        try {
            if (c != null) { // must close cursor regardless of the count and the condition of not affected by this way
                if (c.getCount() > 0 && c.moveToFirst()) {
                    do {
                        DashboardActivity.extractFromCursor(c);
                    } while (c.moveToNext());
                }
                c.close();
            }
        } catch (Exception e) {
            c.close();
        }
    }

    public static void iterateCursor(Cursor c, ParameterCallback<Cursor> callback) {
        // http://stackoverflow.com/questions/14316082/cursor-window-could-not-be-created-from-binder
        try {
            if (c
                    != null) { // must close cursor regardless of the count and the condition of not affected by this way
                if (c.getCount() > 0 && c.moveToFirst()) {
                    do {
                        try {
                            callback.onResponse(c);
                        } catch (Exception e) {
                            Log.e(TAG, "iterateCursor: ", e);
                        }
                    } while (c.moveToNext());
                }
                c.close();
            }
        } catch (Exception e) {
            c.close();
            Log.e(TAG, "iterateCursor: ", e);
        }
    }

    public <T> T getFirstEntry(Cursor c, BaseCallback<T, Cursor> callback) {
        T t = null;
        try {
            if (c != null) {
                if (c.getCount() > 0 && c.moveToFirst()) {
                    try {
                        t = callback.onResponse(c);
                    } catch (Exception e) {
                        Log.e(TAG, "iterateCursor: ", e);
                    }
                }
                c.close();
            }
        } catch (Exception e) {
            c.close();
        }

        return t;
    }
}
