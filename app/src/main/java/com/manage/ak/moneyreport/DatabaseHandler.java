package com.manage.ak.moneyreport;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "SmsList";

    private static final String BANK_TABLE = "bankTransactions";
    private static final String CASH_TABLE = "cashTransactions";

    private String CREATE_BANK_TABLE = "CREATE TABLE " + BANK_TABLE + "("
            + KEY_TYPE + " TEXT,"
            + KEY_AMOUNT + " TEXT,"
            + KEY_DATE + " TEXT,"
            + KEY_BALANCE + " TEXT" + ")";

    private String CREATE_CASH_TABLE = "CREATE TABLE " + CASH_TABLE + "("
            + KEY_TYPE + " TEXT,"
            + KEY_AMOUNT + " TEXT,"
            + KEY_DATE + " TEXT,"
            + KEY_BALANCE + " TEXT" + ")";

    private static final String KEY_TYPE = "type";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_DATE = "date";
    private static final String KEY_BALANCE = "balance";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_BANK_TABLE);
        db.execSQL(CREATE_CASH_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BANK_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CASH_TABLE);

        onCreate(db);
    }

    public void addBankSms(Sms sms) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values;
        values = new ContentValues();
        values.put(KEY_TYPE, sms.getMsgType());
        values.put(KEY_AMOUNT, sms.getMsgAmt());
        values.put(KEY_DATE, sms.getMsgDate());
        values.put(KEY_BALANCE, sms.getMsgBal());

        db.insert(BANK_TABLE, null, values);
        db.close();
    }

    public void addCashSms(Sms sms) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values;
        values = new ContentValues();
        values.put(KEY_TYPE, sms.getMsgType());
        values.put(KEY_AMOUNT, sms.getMsgAmt());
        values.put(KEY_DATE, sms.getMsgDate());
        values.put(KEY_BALANCE, sms.getMsgBal());

        db.insert(CASH_TABLE, null, values);
        db.close();
    }

    public List<Sms> getAllSms(String TABLE) {
        List<Sms> smsList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToLast()) {
            do {
                Sms sms = new Sms();
                sms.setMsgType(cursor.getString(0));
                sms.setMsgAmt(cursor.getString(1));
                sms.setMsgDate(cursor.getString(2));
                sms.setMsgBal(cursor.getString(3));

                smsList.add(sms);
            } while (cursor.moveToPrevious());
        }

        return smsList;
    }
}
