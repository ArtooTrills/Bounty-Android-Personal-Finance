package com.example.earthshaker.moneybox.transaction.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.earthshaker.moneybox.common.dao.db.Contract;
import com.example.earthshaker.moneybox.transaction.TransactionConfig;

/**
 * Created by earthshaker on 15/5/17.
 */

public class TransactionOrm {

    public static ContentValues getContentValues(TransactionConfig transactionConfig) {
        ContentValues cv = new ContentValues();
        cv.put(Contract.Transactions.COLUMN_NAME_AMOUNT, transactionConfig.getAmount());
        cv.put(Contract.Transactions.COLUMN_NAME_EXPENSE_CATEGORY, transactionConfig.getCategory());
        cv.put(Contract.Transactions.COLUMN_NAME_IS_EXPENSE, transactionConfig.getExpense());
        cv.put(Contract.Transactions.COLLUMN_NAME_DATE, transactionConfig.getDate());
        return cv;
    }

    public static TransactionConfig getTransactionCOnfig(Cursor cursor) {
        TransactionConfig transactionConfig = new TransactionConfig();
        boolean status = cursor.getInt(cursor.getColumnIndex(Contract.Transactions.COLUMN_NAME_IS_EXPENSE)) == 1;
        transactionConfig.setExpense(status);
        transactionConfig.setDate(cursor.getString(cursor.getColumnIndex(Contract.Transactions.COLLUMN_NAME_DATE)));
        transactionConfig.setAmount(cursor.getDouble(cursor.getColumnIndex(Contract.Transactions.COLUMN_NAME_AMOUNT)));
        transactionConfig.setCategory(cursor.getString(cursor.getColumnIndex(Contract.Transactions.COLUMN_NAME_EXPENSE_CATEGORY)));
        transactionConfig.setId(cursor.getString(cursor.getColumnIndex(Contract.Transactions._ID)));
        return transactionConfig;
    }
}
