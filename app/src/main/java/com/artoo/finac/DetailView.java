package com.artoo.finac;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DetailView extends Fragment {

    private SQLiteDatabase db;
    private SharedPreferences settings;

    private DetailViewAdapter adapterExpense;
    private Cursor cursorExpense;
    private DetailViewAdapter adapterEarnings;
    private Cursor cursorEarnings;

    private RelativeLayout cardHeaderEarnings;
    private RelativeLayout cardHeaderExpenses;
    private RelativeLayout cardFooterEarnings;
    private RelativeLayout cardFooterExpenses;
    private TextView editTextEarningsValue;
    private TextView editTextExpensesValue;

    private final static String TAG = "Finac Detail View";

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //
            Log.d(TAG, "Broadcast Received!!");
            changeCursor();
        }
    };

    //  FN
    private void changeCursor() {

        updateData();
        adapterExpense.changeCursor(cursorExpense);
        adapterEarnings.changeCursor(cursorEarnings);
    }

    private void updateData() {

        //  The Global Data Update
        editTextExpensesValue.setText("₹ " + String.valueOf(settings.getFloat("debit",0.0f)) + " /-");
        editTextEarningsValue.setText("₹ " + String.valueOf(settings.getFloat("credit", 0.0f)) + " /-");

        //  Fetch Data from DB and update the ArrayList;
        String queryExpenses = "SELECT `_id`, SUM(`amount`) AS amount, `category` FROM (SELECT * FROM `transaction` WHERE type = 'DB') GROUP BY `category`";
        String queryEarnings = "SELECT `_id`, SUM(`amount`) AS amount, `category` FROM (SELECT * FROM `transaction` WHERE type = 'CR') GROUP BY `category`";
        try {

            if (cursorExpense != null)
                cursorExpense.close();
            
            if (cursorEarnings != null)
                cursorEarnings.close();

            cursorExpense = db.rawQuery(queryExpenses, null);
            cursorEarnings = db.rawQuery(queryEarnings, null);
        } catch (SQLException e) {

            Log.d(TAG, "SQL Exception");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.detail_view, container, false);

        //  Referencing
        db = getActivity().openOrCreateDatabase(Constants.DB_NAME, Context.MODE_PRIVATE, null);
        settings = PreferenceManager.getDefaultSharedPreferences(getActivity());

        cardHeaderEarnings = (RelativeLayout) view.findViewById(R.id.cardHeaderEarnings);
        cardHeaderExpenses = (RelativeLayout) view.findViewById(R.id.cardHeaderExpenses);
        cardFooterEarnings = (RelativeLayout) view.findViewById(R.id.cardFooterEarnings);
        cardFooterExpenses = (RelativeLayout) view.findViewById(R.id.cardFooterExpenses);
        editTextEarningsValue = (TextView) view.findViewById(R.id.editTextEarningsValue);
        editTextExpensesValue = (TextView) view.findViewById(R.id.editTextExpensesValue);

        cardHeaderEarnings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int vis = cardFooterEarnings.getVisibility();
                cardFooterEarnings.setVisibility(vis == View.VISIBLE ? View.GONE : View.VISIBLE);

                cardFooterExpenses.setVisibility(View.GONE);
            }
        });

        cardHeaderExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int vis = cardFooterExpenses.getVisibility();
                cardFooterExpenses.setVisibility(vis == View.VISIBLE ? View.GONE : View.VISIBLE);

                cardFooterEarnings.setVisibility(View.GONE);
            }
        });

        updateData();
        adapterExpense = new DetailViewAdapter(getActivity(), cursorExpense);
        adapterEarnings = new DetailViewAdapter(getActivity(), cursorEarnings);

        ListView listViewExpenses = (ListView) view.findViewById(R.id.listViewExpenses);
        listViewExpenses.setAdapter(adapterExpense);

        ListView listViewEarnings = (ListView) view.findViewById(R.id.listViewEarnings);
        listViewEarnings.setAdapter(adapterEarnings);

        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.COM_ARTOO_FINAC_ADDED_TRANSACTIONS);
        getActivity().registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }
}
