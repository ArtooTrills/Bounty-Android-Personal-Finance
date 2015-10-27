package bounty.android.example.com.bounty;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by RamizMehran on 27/10/2015.
 */
public class MyDBHandlerSms extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "smsDB.db";
    private static final String TABLE_SMS = "sms";

    public static final String COLUMN_FROM = "frm";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_AMOUNT = "amount";


    public MyDBHandlerSms(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SMS_TABLE = "CREATE TABLE " +
                TABLE_SMS + "("
                + COLUMN_FROM + " TEXT," + COLUMN_TYPE
                + " TEXT," + COLUMN_AMOUNT + " TEXT )";
        db.execSQL(CREATE_SMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SMS);
        onCreate(db);
    }

    public void addSms(Sms product) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_FROM, product.getFrom());
        values.put(COLUMN_TYPE, product.getType());
        values.put(COLUMN_AMOUNT, product.getAmount());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_SMS, null, values);
        db.close();
    }

    public ArrayList<Sms> findSms() {
        String query = "Select * FROM " + TABLE_SMS;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Sms> smsData= new ArrayList<Sms>();
        //Iterating through all the rows
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            Sms product = new Sms();

            product.setFrom(cursor.getString(0));
            product.setType(cursor.getString(1));
            product.setAmount(cursor.getString(2));
            Log.d("List Values", "from: " + cursor.getString(0) + " type:" + cursor.getString(1) + " amount:" + cursor.getString(2));
            Log.d("List Values", "from: " + product.getFrom() + " type:" + product.getType() + " amount:" + product.getAmount());
            smsData.add(product);
            Sms src = smsData.get(0);
            Log.d("updateTable", src.getFrom() + " " + src.getType() + " " + src.getAmount());
            while(cursor.moveToNext()){
                Sms product1 = new Sms();

                product1.setFrom(cursor.getString(0));
                product1.setType(cursor.getString(1));
                product1.setAmount(cursor.getString(2));
                Log.d("List Values", "from: " + cursor.getString(0) + " type:" + cursor.getString(1) + " amount:" + cursor.getString(2));
                Log.d("List Values", "from: " + product1.getFrom() + " type:" + product1.getType() + " amount:" + product1.getAmount());
                smsData.add(product1);
            }
        }

        for(int i = 0; i < smsData.size(); i++){
            Sms src = smsData.get(i);
            Log.d("updateTable",src.getFrom()+" "+src.getType()+" "+src.getAmount());
        }

        db.close();
        return smsData;
    }
}