package com.artoo.finac;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class DetailView extends Fragment {

    private SQLiteDatabase db;

    private DetailViewAdapter adapter;
    private Cursor cursor;

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
        adapter.changeCursor(cursor);
    }

    private void updateData() {

        //  Fetch Data from DB and update the ArrayList;
        String query = "SELECT * from `transaction` GROUP BY `category`";
        try {

            if (cursor != null)
                cursor.close();

            cursor = db.rawQuery(query, null);
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

        updateData();
        adapter = new DetailViewAdapter(getActivity(), cursor);

        ListView listViewDetails = (ListView) view.findViewById(R.id.listViewDetails);
        listViewDetails.setAdapter(adapter);

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
