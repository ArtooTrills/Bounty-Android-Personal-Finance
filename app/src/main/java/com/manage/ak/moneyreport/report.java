package com.manage.ak.moneyreport;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class report extends AppCompatActivity {

    GraphView graph;
    RecyclerView recyclerView;
    ReportAdapter reportAdapter;
    TextView itemDate;
    Spinner options;
    String optionSelected;

    List<Sms> smsList = new ArrayList<>();
    List<ReportItem> adapterList = new ArrayList<>();
    List<ReportItem> reportList = new ArrayList<>();
    List<List<ReportItem>> itemList = new ArrayList<>();

    Context context = this;

    private String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        graph = (GraphView) findViewById(R.id.graph);

        recyclerView = (RecyclerView) findViewById(R.id.report_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reportAdapter = new ReportAdapter(adapterList, context);
        recyclerView.setAdapter(reportAdapter);

        itemDate = (TextView) findViewById(R.id.itemDate);
        options = (Spinner) findViewById(R.id.graphOptions);
        ArrayAdapter<CharSequence> options_adapter = ArrayAdapter.createFromResource(this, R.array.options, android.R.layout.simple_spinner_item);
        options_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        options.setAdapter(options_adapter);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("DATA");
        color = bundle.getString("color");
        smsList = (ArrayList<Sms>) bundle.getSerializable("SMS");

        toolbar.setBackgroundColor(Color.parseColor(color));

        options.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getItemAtPosition(position).toString()) {
                    case "Daily":
                        adapterList.clear();
                        reportAdapter.notifyDataSetChanged();
                        graph.removeAllSeries();
                        itemDate.setText(" ");
                        reportList = new ArrayList<>();
                        itemList = new ArrayList<>();
                        List<Double> expenses1 = new ArrayList<>();
                        expenses1.add(0, 0.0);
                        List<Long> expenseDay = new ArrayList<>();
                        String day = smsList.get(0).getDay();
                        expenseDay.add(0, smsList.get(0).getDateLong());
                        int i = 0, j = 0, size = 0;
                        for (Sms sms : smsList) {
                            if (sms.getDay().equals(day)) {
                                expenses1.set(i, expenses1.get(i) + sms.getAmtDouble());
                                reportList.add(j, new ReportItem(sms.getMsgType(), sms.getMsgAmt(), sms.getDateLong(), "Daily"));
                                j++;
                                if (size == smsList.size() - 1) {
                                    itemList.add(i, reportList);
                                }
                            } else {
                                itemList.add(i, reportList);
                                i++;
                                expenses1.add(i, sms.getAmtDouble());
                                expenseDay.add(i, sms.getDateLong());

                                reportList = new ArrayList<>();
                                j = 0;
                                reportList.add(j, new ReportItem(sms.getMsgType(), sms.getMsgAmt(), sms.getDateLong(), "Daily"));
                                j++;
                                if (size == smsList.size() - 1) {
                                    itemList.add(i, reportList);
                                }
                                day = sms.getDay();
                            }
                            size++;
                        }

                        DrawGraph(expenses1, expenseDay, "Daily");
                        break;
                    case "Weekly":
                        adapterList.clear();
                        reportAdapter.notifyDataSetChanged();
                        graph.removeAllSeries();
                        itemDate.setText(" ");
                        reportList = new ArrayList<>();
                        itemList = new ArrayList<>();
                        List<Double> expenses2 = new ArrayList<>();
                        expenses2.add(0, 0.0);
                        List<Long> expenseWeek = new ArrayList<>();
                        String week = smsList.get(0).getWeek();
                        expenseWeek.add(0, smsList.get(0).getDateLong());
                        int i1 = 0, j1 = 0, size1 = 0;
                        for (Sms sms : smsList) {
                            if (sms.getWeek().equals(week)) {
                                expenses2.set(i1, expenses2.get(i1) + sms.getAmtDouble());
                                reportList.add(j1, new ReportItem(sms.getMsgType(), sms.getMsgAmt(), sms.getDateLong(), "Weekly"));
                                j1++;
                                if (size1 == smsList.size() - 1) {
                                    itemList.add(i1, reportList);
                                }
                            } else {
                                itemList.add(i1, reportList);
                                i1++;
                                expenses2.add(i1, sms.getAmtDouble());
                                expenseWeek.add(i1, sms.getDateLong());

                                reportList = new ArrayList<>();
                                j1 = 0;
                                reportList.add(j1, new ReportItem(sms.getMsgType(), sms.getMsgAmt(), sms.getDateLong(), "Weekly"));
                                j1++;
                                if (size1 == smsList.size() - 1) {
                                    itemList.add(i1, reportList);
                                }
                                week = sms.getWeek();
                            }
                            size1++;
                        }

                        DrawGraph(expenses2, expenseWeek, "Weekly");
                        break;
                    case "Monthly":
                        adapterList.clear();
                        reportAdapter.notifyDataSetChanged();
                        graph.removeAllSeries();
                        itemDate.setText(" ");
                        reportList = new ArrayList<>();
                        itemList = new ArrayList<>();
                        List<Double> expenses3 = new ArrayList<>();
                        expenses3.add(0, 0.0);
                        List<Long> expenseMonth = new ArrayList<>();
                        String month = smsList.get(0).getMonth();
                        expenseMonth.add(0, smsList.get(0).getDateLong());
                        int i3 = 0, j3 = 0, size3 = 0;
                        for (Sms sms : smsList) {
                            if (sms.getMonth().equals(month)) {
                                expenses3.set(i3, expenses3.get(i3) + sms.getAmtDouble());
                                reportList.add(j3, new ReportItem(sms.getMsgType(), sms.getMsgAmt(), sms.getDateLong(), "Monthly"));
                                j3++;
                                if (size3 == smsList.size() - 1) {
                                    itemList.add(i3, reportList);
                                }
                            } else {
                                itemList.add(i3, reportList);
                                i3++;
                                expenses3.add(i3, sms.getAmtDouble());
                                expenseMonth.add(i3, sms.getDateLong());

                                reportList = new ArrayList<>();
                                j3 = 0;
                                reportList.add(j3, new ReportItem(sms.getMsgType(), sms.getMsgAmt(), sms.getDateLong(), "Monthly"));
                                j3++;
                                if (size3 == smsList.size() - 1) {
                                    itemList.add(i3, reportList);
                                }
                                month = sms.getMonth();
                            }
                            size3++;
                        }

                        DrawGraph(expenses3, expenseMonth, "Monthly");

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                optionSelected = parent.getItemAtPosition(0).toString();
            }
        });

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void DrawGraph(List<Double> expenses, List<Long> expenseDay, String type) {

        int length = expenses.size();

        if (length < 2) {
            expenseDay.add(length, System.currentTimeMillis());
            expenses.add(length, 0.0);
            length = 2;
        }

        if (length > 6) {
            length = 6;
        }

        double[] e = new double[length];
        for (int z = 0; z < length; z++) {
            e[z] = expenses.get(z);
        }

        String[] w = new String[length];
        switch (type) {
            case "Daily":
                for (int z = 0; z < length; z++) {
                    w[z] = new SimpleDateFormat("dd/MM").format(new Date(expenseDay.get(z)));
                }
                break;
            case "Weekly":
                for (int z = 0; z < length; z++) {
                    w[z] = "Week:" + new SimpleDateFormat("W").format(new Date(expenseDay.get(z))) + " of " + new SimpleDateFormat("MMM").format(new Date(expenseDay.get(z)));
                }
                break;
            case "Monthly":
                for (int z = 0; z < length; z++) {
                    w[z] = new SimpleDateFormat("MMM").format(new Date(expenseDay.get(z))) + "'" + new SimpleDateFormat("yy").format(new Date(expenseDay.get(z)));
                }
                break;
        }

        double max = 0;
        DataPoint[] dataPoints = new DataPoint[length];
        for (int k = 0; k < length; k++) {
            dataPoints[k] = new DataPoint(k, e[k]);
            if (e[k] > max) {
                max = e[k];
            }
        }

        final String t = type;

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dataPoints);
        graph.removeAllSeries();
        graph.addSeries(series);
        series.setColor(Color.parseColor(color));
        series.setSpacing(10);
        series.setValuesOnTopColor(Color.BLACK);
        series.setDrawValuesOnTop(true);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                adapterList.clear();
                adapterList.addAll(itemList.get((int) dataPoint.getX()));
                switch (t) {
                    case "Daily":
                        itemDate.setText(itemList.get((int) dataPoint.getX()).get(0).getReportDate());
                        break;
                    case "Weekly":
                        itemDate.setText(itemList.get((int) dataPoint.getX()).get(0).getReportWeek());
                        break;
                    case "Monthly":
                        itemDate.setText(itemList.get((int) dataPoint.getX()).get(0).getReportMonth());
                }
                reportAdapter.notifyDataSetChanged();
            }
        });

        graph.setTitle("Expense Chart");
        graph.setTitleTextSize(40);
        graph.setTitleColor(Color.parseColor(color));

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(length - 1);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(max + 5000);

        graph.setBackgroundColor(Color.parseColor("#ffffff"));
        graph.getGridLabelRenderer().setGridColor(Color.TRANSPARENT);
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.BLACK);
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.BLACK);

        StaticLabelsFormatter s = new StaticLabelsFormatter(graph);
        s.setHorizontalLabels(w);
        graph.getGridLabelRenderer().setLabelFormatter(s);
    }
}
