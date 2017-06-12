package com.examples.ankit.breakpoint.prefences;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.examples.ankit.breakpoint.BreakPointApplication;
import com.examples.ankit.breakpoint.Gson;
import com.examples.ankit.breakpoint.models.Transactions;

/**
 * This is a helper class to save and read from preference.
 * Created by ankit on 20/05/17.
 */

public class MyPreferenceManager {
    private static final String PREF_USER_CONSENT = "user_consent";
    private static final String PREF_TRANSACTIONS = "transactions";

    /**
     * Saves if user has accepted agreement.
     */
    public static void setUserConsent(boolean userConsent) {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(BreakPointApplication.getAppContext());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(PREF_USER_CONSENT, userConsent);
        editor.apply();
    }

    /**
     * return status of acceptence of user agreement.
     */
    public static boolean getUserConsent() {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(BreakPointApplication.getAppContext());
        return sharedPrefs.getBoolean(PREF_USER_CONSENT, false);
    }

    /**
     * Saves all transactions.
     */
    public static void setTransactions(Transactions transactions) {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(BreakPointApplication.getAppContext());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(PREF_TRANSACTIONS, Gson.getInstance().toJson(transactions));
        editor.apply();
    }

    /**
     * returns list of transactions.
     */
    public static Transactions getTransactions() {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(BreakPointApplication.getAppContext());
        return Gson.getInstance().fromJson(sharedPrefs.getString(PREF_TRANSACTIONS, null), Transactions.class);
    }

    /**
     * return last sync time
     */
    public static long getLastTransactionUpdateTime() {
        long lastCheckedTime = 0;
        Transactions transactions = getTransactions();
        if (transactions != null) {
            lastCheckedTime = transactions.getLastChecked();
        }
        return lastCheckedTime;
    }


}
