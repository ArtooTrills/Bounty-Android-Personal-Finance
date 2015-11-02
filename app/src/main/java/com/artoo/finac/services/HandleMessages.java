package com.artoo.finac.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HandleMessages extends BroadcastReceiver {

    //  DM
    private static Context context;
    private final SmsManager sms = SmsManager.getDefault();

    private final static String TAG = "Finac SMS";

    //  Singleton Implementation
//    private static HandleMessages instance;
//    private HandleMessages() {
//    }
//
//    public static HandleMessages getInstance(Context c) {
//
//        context = c;
//
//        if (instance == null)
//            instance = new HandleMessages();
//
//        return instance;
//    }

    @Override
    public void onReceive(Context c, Intent intent) {

        context = c;
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        if (settings.getBoolean("readMessages",true)) {

            final Bundle bundle = intent.getExtras();

            try {

                if (bundle != null) {

                    final Object[] pdusObj = (Object[]) bundle.get("pdus");

                    for (int i = 0; i < pdusObj.length; i++) {

                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                        String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                        String senderNum = phoneNumber;
                        String message = currentMessage.getDisplayMessageBody();

                        doParsingAndUpdateDB(message.toLowerCase(), senderNum);
                    }
                }
            } catch (Exception e) {
                Log.e("SmsReceiver", "Exception smsReceiver" + e);
            }
        }
    }

    private void doParsingAndUpdateDB(String message, String senderNum) {

        double amt;
        String type;

        Pattern regEx  = Pattern.compile("[Ii][Nn][Rr](\\s*.\\s*\\d*)");
        Pattern regEx2 = Pattern.compile("[rR][sS](\\s*.\\s*\\d*)");

        Matcher matcher  = regEx.matcher(message);
        Matcher matcher2 = regEx2.matcher(message);

        if (message.contains("deposited") || message.contains("credited") || message.contains("cr")) {

            type = "CR";
        } else if (message.contains("withdrawn") || message.contains("debited") || message.contains("dr")) {

            type = "DB";
        }
        else
            //  No Transaction Tyoe Message found
            return;

        if (matcher.find()) {

            String match = matcher.group();
            match = match.replaceAll("([Ii][Nn][Rr])*([Rr][Ss])*","");
            amt = Double.parseDouble(match);

            //
        } else if (matcher2.find()) {

            String match = matcher2.group();
            match = match.replaceAll("([Ii][Nn][Rr])*([Rr][Ss])*","");
            amt = Double.parseDouble(match);

        } else
            return;

        Log.d(TAG, "Fetched Amount" + amt + ", Type: " + type);

        //  Time to Enter the DB and then broadcast and need to update the Preference too.
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();

        if (type.equals("CR")){

            Float val = settings.getFloat("credit", 0.0f);
            editor.putFloat("credit", (float) (val+amt));
        }
        else {

            Float val = settings.getFloat("debit", 0.0f);
            editor.putFloat("debit", (float) (val+amt));
        }

        editor.apply();

        SQLiteDatabase db = context.openOrCreateDatabase("finac",Context.MODE_PRIVATE,null);
        String query;
        if (type.equals("CR")) {

            query = "INSERT INTO `transaction` (`amount`, `category`, `type`) VALUES(" + amt + ", '" + "Salary" + "', '" + type + "')";
        } else {

            query = "INSERT INTO `transaction` (`amount`, `category`, `type`) VALUES(" + amt + ", '" + "Other Sources" + "', '" + type + "')";
        }
        db.execSQL(query);
        Log.d(TAG, 2 + "");

        //  Need to update the

        Intent intent = new Intent();
        intent.setAction("COM_ARTOO_FINAC_ADDED_TRANSACTIONS");
        Log.d(TAG, 3 + "");
        context.sendBroadcast(intent);
        Log.d(TAG, 4 + "");
    }
}