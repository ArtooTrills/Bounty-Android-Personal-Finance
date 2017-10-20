package com.example.nazmuddinmavliwala.ewallet.data.disc;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by nazmuddinmavliwala on 03/03/17.
 */


@Singleton
public class SharedPrefManager implements SharedPrefService {


    private final SharedPreferences preferences;

    @Inject
    public SharedPrefManager(@ApplicationContext Context context) {
        preferences = context.getSharedPreferences(
                SharedPrefConstants.SHARED_PREF_NAME
                , Context.MODE_PRIVATE);
    }

    @Override
    public void storeValue(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key,value);
        editor.apply();
    }

    @Override
    public void storeValue(String key, float value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key,value);
        editor.apply();
    }

    @Override
    public void storeValue(String key, long value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key,value);
        editor.apply();
    }

    @Override
    public void storeValue(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

    @Override
    public void storeValue(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    @Override
    public void storeValue(String key, Set<String> value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(key, value);
        editor.apply();
    }

    @Override
    public float retrieveValue(String key, float defaultValue) {
        return preferences.getFloat(key,defaultValue);
    }

    @Override
    public long retrieveValue(String key, long defaultValue) {
        return preferences.getLong(key,defaultValue);
    }

    @Override
    public String retrieveValue(String key, String defaultValue) {
        return preferences.getString(key,defaultValue);
    }

    @Override
    public boolean retrieveValue(String key, boolean defaultValue) {
        return preferences.getBoolean(key,defaultValue);
    }

    @Override
    public int retrieveValue(String key, int defaultValue) {
        return preferences.getInt(key,defaultValue);
    }

    @Override
    public Set<String> retrieveValue(String key, Set<String> defaultValue) {
        return preferences.getStringSet(key,defaultValue);
    }

    @Override
    public Map<String, ?> retrieveAll() {
        return preferences.getAll();
    }

    @Override
    public void removeValue(String key) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key).apply();
    }

    @Override
    public void clearPref() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear().apply();
    }

    @Override
    public boolean isKeyPresent(String key) {
        return preferences.contains(key);
    }
}
