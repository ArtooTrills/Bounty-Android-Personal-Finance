package com.example.nazmuddinmavliwala.ewallet.data.disc;

import java.util.Map;
import java.util.Set;

/**
 * Created by nazmuddinmavliwala on 12/01/17.
 */
public interface SharedPrefService {

    void storeValue(String key, int value);

    void storeValue(String key, float value);

    void storeValue(String key, long value);

    void storeValue(String key, boolean value);

    void storeValue(String key, String value);

    void storeValue(String key, Set<String> value);

    float retrieveValue(String key, float defaultValue);

    long retrieveValue(String key, long defaultValue);

    String retrieveValue(String key, String defaultValue);

    boolean retrieveValue(String key, boolean defaultValue);

    int retrieveValue(String key, int defaultValue);

    Set<String> retrieveValue(String key, Set<String> defaultValue);

    Map<String, ?> retrieveAll();

    void removeValue(String key);

    void clearPref();

    boolean isKeyPresent(String key);
}
