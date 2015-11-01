package com.artoo.finac;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class SummaryView extends Fragment {

    //  DM
    private SharedPreferences settings;
    private SQLiteDatabase db;

    private PieChart pieChart;

    private final static String TAG = "Finac SummaryView";
    private ArrayList<Entry> yValues;
    private ArrayList<String> xValues;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //
            Log.d(TAG, "Broadcast Received!!");
            updateData();
        }
    };

    //  CL
    private class LoadTask extends AsyncTask<String, Void, Void> {

        boolean doNothing = false;
        float credit;
        float debit;
        float savings;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            xValues = new ArrayList<>();
            yValues = new ArrayList<>();

            credit = settings.getFloat("credit",0);
            debit = settings.getFloat("debit",0);
            savings = credit - debit;
        }

        @Override
        protected Void doInBackground(String... params) {

            //  Load data from SQL;        //  Referencing
        settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        db = getActivity().openOrCreateDatabase(Constants.DB_NAME, Context.MODE_PRIVATE, null);

            String query = "SELECT `amount`, `category` FROM (SELECT * FROM `transaction` WHERE type = 'DB') GROUP BY `category`";
//            String query = "SELECT `amount`, `category` FROM `transaction` GROUP BY `category` ORDER BY `amount` ASC WHERE `type` = 'DB'";
            try {

                Cursor cursor = db.rawQuery(query, null);

                int i=0;
                while (cursor.moveToNext()) {

                    String cat = cursor.getString(cursor.getColumnIndex("category"));
                    float sav = cursor.getFloat(cursor.getColumnIndex("amount"));

                    if (!cat.equals("Savings")) {

                        yValues.add(new Entry(sav, i++));
                        xValues.add(cat);
                    } else {

                        //savings += sav;
                    }
                }
            } catch (Exception e) {

                Log.d(TAG, "SQL Query Exception!");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (!doNothing) {

                pieChart.setUsePercentValues(true);
                pieChart.setDescription("");
                pieChart.setDrawHoleEnabled(true);

                pieChart.setHoleColorTransparent(true);
                pieChart.setHoleRadius(15);
                pieChart.setTransparentCircleRadius(20);

                PieDataSet pieDataSet = new PieDataSet(yValues, "");
                pieDataSet.setSliceSpace(10);
                pieDataSet.setSelectionShift(10);

                Legend legend = pieChart.getLegend();
                legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
                legend.setXEntrySpace(7);
                legend.setYEntrySpace(5);
                legend.setEnabled(false);

                ArrayList<Integer> colors = new ArrayList<>();
                for (int c : ColorTemplate.LIBERTY_COLORS)
                    colors.add(c);

                pieDataSet.setColors(colors);

                PieData pieData = new PieData(xValues, pieDataSet);
                pieData.setValueFormatter(new PercentFormatter());
                pieData.setValueTextSize(12f);

                pieChart.setData(pieData);

                pieChart.setRotation(0);
                pieChart.setRotationEnabled(true);

                pieChart.invalidate();
            }
        }
    }

    //  FN
    private void updateData() {

        LoadTask loadTask = new LoadTask();
        loadTask.execute();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.summary_view, container, false);

        //  Referencing
        settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        db = getActivity().openOrCreateDatabase(Constants.DB_NAME, Context.MODE_PRIVATE, null);

        pieChart            = (PieChart) view.findViewById(R.id.pieChartSummary);

        new LoadTask().execute();

        return view;
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
