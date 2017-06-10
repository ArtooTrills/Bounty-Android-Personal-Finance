package com.example.earthshaker.moneybox.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.earthshaker.moneybox.R;

import java.util.Set;

/**
 * Created by earthshaker on 13/5/17.
 */
public class SharedPrefsUtils {

    public SharedPrefsUtils() {
    }

    public String getStringDataByKey(String key, String defaultValue, Context context) {
        final SharedPreferences preferences =
                context.getSharedPreferences(SharedPreferenceConstants.MONEY_BOX_PREFS, Context.MODE_PRIVATE);

        return preferences.getString(key, defaultValue);
    }

    public int getIntDataByKey(String key, int defaultValue, Context context) {
        final SharedPreferences preferences =
                context.getSharedPreferences(SharedPreferenceConstants.MONEY_BOX_PREFS, Context.MODE_PRIVATE);

        return preferences.getInt(key, defaultValue);
    }

    public long getLongDataByKey(String key, long defaultValue, Context context) {
        final SharedPreferences preferences =
                context.getSharedPreferences(SharedPreferenceConstants.MONEY_BOX_PREFS, Context.MODE_PRIVATE);

        return preferences.getLong(key, defaultValue);
    }

    public boolean getBooleanDataByKey(String key, Context context) {
        return getBooleanDataByKey(key, false, context);
    }

    public boolean getBooleanDataByKey(String key, boolean defaultValue, Context context) {
        final SharedPreferences preferences =
                context.getSharedPreferences(SharedPreferenceConstants.MONEY_BOX_PREFS, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, defaultValue);
    }

    public Set<String> getStringSetByKey(String key, Context context) {
        final SharedPreferences preferences =
                context.getSharedPreferences(SharedPreferenceConstants.MONEY_BOX_PREFS, Context.MODE_PRIVATE);
        return preferences.getStringSet(key, null);
    }

    public void putBoolean(String key, boolean value, Context context) {
        final SharedPreferences.Editor editor =
                context.getSharedPreferences(SharedPreferenceConstants.MONEY_BOX_PREFS, Context.MODE_PRIVATE)
                        .edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void putString(String key, String value, Context context) {
        final SharedPreferences.Editor editor =
                context.getSharedPreferences(SharedPreferenceConstants.MONEY_BOX_PREFS, Context.MODE_PRIVATE)
                        .edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void putInt(String key, Integer value, Context context) {
        final SharedPreferences.Editor editor =
                context.getSharedPreferences(SharedPreferenceConstants.MONEY_BOX_PREFS, Context.MODE_PRIVATE)
                        .edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void putStringSet(String key, Set value, Context context) {
        final SharedPreferences.Editor editor =
                context.getSharedPreferences(SharedPreferenceConstants.MONEY_BOX_PREFS, Context.MODE_PRIVATE)
                        .edit();
        editor.putStringSet(key, value);
        editor.apply();
    }

    public void putLong(String key, Long value, Context context) {
        final SharedPreferences.Editor editor =
                context.getSharedPreferences(SharedPreferenceConstants.MONEY_BOX_PREFS, Context.MODE_PRIVATE)
                        .edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public void putFloat(String key, Float value, Context context) {
        final SharedPreferences.Editor editor =
                context.getSharedPreferences(SharedPreferenceConstants.MONEY_BOX_PREFS, Context.MODE_PRIVATE)
                        .edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public void removeKey(String key, Context context) {
        final SharedPreferences.Editor editor =
                context.getSharedPreferences(SharedPreferenceConstants.MONEY_BOX_PREFS, Context.MODE_PRIVATE)
                        .edit();
        editor.remove(key);
        editor.apply();
    }

    public boolean containsKey(String key, Context context) {
        final SharedPreferences preferences =
                context.getSharedPreferences(SharedPreferenceConstants.MONEY_BOX_PREFS, Context.MODE_PRIVATE);
        return preferences.contains(key);
    }
}
