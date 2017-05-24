package com.examples.ankit.breakpoint.sms;

import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.examples.ankit.breakpoint.BreakPointApplication;
import com.examples.ankit.breakpoint.models.Transaction;
import com.examples.ankit.breakpoint.models.Transactions;
import com.examples.ankit.breakpoint.prefences.MyPreferenceManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ankit on 20/05/17.
 */

public class SmsUtil {
    private static final String TAG = "SMSUtil";
    public static final int EXPENSE = 0;
    public static final int INCOME = 1;
    public static final int UNDEFINED = -1;

    /**
     * Fetch SMS and save into local pref based on transaction type.
     * */
    public static Transactions fetchInbox() {
        Transactions transactions = new Transactions();

        Uri uriSms = Uri.parse("content://sms/inbox");
        Cursor cursor = BreakPointApplication.getAppContext().getContentResolver()
                .query(uriSms, new String[]{"_id", "address", "date", "body"}, null, null, "date ASC");
        if (cursor != null && cursor.isBeforeFirst()) {
            ArrayList<Transaction> transactionsList = new ArrayList<>();
            while (cursor.moveToNext()) {
                String senderAddress = cursor.getString(1);
                String messageBody = cursor.getString(3);
                if (isItTransactionalSms(senderAddress)) {
                    int transactionType = getTransactionType(messageBody);
                    if (UNDEFINED != transactionType) {
                        Transaction transaction = new Transaction();
                        try {
                            long timestamp = Long.parseLong(cursor.getString(2));
                            if(timestamp < MyPreferenceManager.getLastTransactionUpdateTime()){
                                break;
                            }
                            transaction.setDate(new Date(timestamp));
                        } catch (NumberFormatException e){
                            e.printStackTrace();
                        }
                        transaction.setAmount(getAmount(messageBody));
                        transaction.setType(transactionType);
                        transactionsList.add(transaction);
                    }
                }
            }

            if(!transactionsList.isEmpty()) {
                transactions.setTransactions(transactionsList);
            }
            transactions.setLastChecked(System.currentTimeMillis());
        }
        return transactions;
    }

    /**
     * Checks and return if a given SMS is from Special Number
     * */
    private static boolean isItTransactionalSms(String senderAddress) {
        if (TextUtils.isEmpty(senderAddress)) {
            return false;
        }

        boolean isFromBank = false;
        Pattern regEx =
                Pattern.compile("[a-zA-Z0-9]{2}-[a-zA-Z0-9]{6}");
        Matcher matcher = regEx.matcher(senderAddress);
        if (matcher.find()) {
            isFromBank = true;
        }
        return isFromBank;
    }

    /**
     * Returns amount from an SMS.
     * */
    private static double getAmount(String messageBody) {
        Pattern regEx
                = Pattern.compile("(?i)(?:(?:RS|INR|MRP)\\.?\\s?)(\\d+(:?\\,\\d+)?(\\,\\d+)?(\\.\\d{1,2})?)");
        Matcher matcher = regEx.matcher(messageBody);
        if (matcher.find()) {
            String amount = (matcher.group(0));
            //(?i) will search for case insensitive
            amount = amount.replaceAll("(?i)rs.", "");
            amount = amount.replaceAll("(?i)inr.", "");
            amount = amount.replaceAll(" ", "");
            amount = amount.replaceAll(",", "");
            return Double.parseDouble(amount);
        }

        return 0;
    }

    /**
     * It reads sms body and based on keywords, return transaction type as Expense or Income
     * */
    private static int getTransactionType(String messageBody) {
        int transactionType = UNDEFINED;
        if (messageBody.contains("paid") || messageBody.contains("debited") || messageBody.contains("spent") ||
                messageBody.contains("purchase") || messageBody.contains("dr")) {
            transactionType = EXPENSE;
        } else if (messageBody.contains("credited") || messageBody.contains("cr")
                || messageBody.contains("received") || messageBody.contains("receive")) {
            transactionType = INCOME;
        }

        return transactionType;
    }
}
