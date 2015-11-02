package com.artoo.finac;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

public class Transaction extends AppCompatActivity {

    //  DM
    private ListView listViewTransactions;
    private TransactionAdapter adapter;

    private SharedPreferences settings;
    private SQLiteDatabase db;

    private Cursor cursor;

    private final static String TAG = "Finac Transaction";

    //  CL
    private class LoadData extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {

            String query = "SELECT * FROM `transaction` ORDER BY `timestamp` DESC";
            try {

                if (cursor != null)
                    cursor.close();

                cursor = db.rawQuery(query, null);
            } catch (Exception e) {

                Log.d(TAG, "SQL Exception");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (cursor!= null) {

                adapter = new TransactionAdapter(Transaction.this, cursor);
                listViewTransactions.setAdapter(adapter);
            }
        }
    }

    //  FN
    private void setupActionBar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        setupActionBar();

        //  Referencing
        settings = PreferenceManager.getDefaultSharedPreferences(Transaction.this);
        db = openOrCreateDatabase(Constants.DB_NAME, Context.MODE_PRIVATE, null);

        listViewTransactions = (ListView) findViewById(R.id.listViewTransactions);

        new LoadData().execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null)
            cursor.close();
    }
}
