package com.examples.ankit.breakpoint.reports;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.examples.ankit.breakpoint.R;
import com.examples.ankit.breakpoint.models.Transaction;
import com.examples.ankit.breakpoint.models.Transactions;
import com.examples.ankit.breakpoint.prefences.MyPreferenceManager;
import com.examples.ankit.breakpoint.sms.SmsUtil;
import com.examples.ankit.breakpoint.view.DayAxisValueFormatter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This fragment shows all expenses and income in month view.
 */
public class MonthlyExpenseFragment extends Fragment {

    private static final String TAG = "MonthlyExpenseFragment";
    @BindView(R.id.monthly_bar_chart)
    BarChart mChart;
    private int FirstMonth = -1;

    public MonthlyExpenseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monthly_expense, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeData();
        initializeChartView();
    }

    private void initializeChartView() {
        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart);

        YAxis yRightAxis = mChart.getAxisRight();
        yRightAxis.setEnabled(false);
        yRightAxis.setDrawZeroLine(false);
        yRightAxis.setDrawAxisLine(false);

        YAxis yLeftAxis = mChart.getAxisLeft();
        yLeftAxis.setDrawZeroLine(false);
        yLeftAxis.setDrawGridLines(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(12);
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setCenterAxisLabels(true);

        Description description = new Description();
        description.setText(getString(R.string.monthly_report));
        mChart.setDescription(description);
        mChart.setTouchEnabled(false);
        mChart.animateY(2000);
    }

    private void initializeData() {
        Transactions transactions = MyPreferenceManager.getTransactions();
        Map<Integer, List<Transaction>> monthlyTransactions = transactions.getMonthlyTransactions();
        if (monthlyTransactions == null || monthlyTransactions.isEmpty()) {
            Log.e(TAG, "No Transactions yet.");
            return;
        }

        ArrayList<BarEntry> expenseBarEntries = new ArrayList<>();
        ArrayList<BarEntry> incomeBarEntries = new ArrayList<>();

        for (int month : monthlyTransactions.keySet()) {
            long totalExpenseOfMonth = 0;
            long totalIncomeOfMonth = 0;
            for (Transaction transaction : monthlyTransactions.get(month)) {
                if(FirstMonth <0 || FirstMonth > month){
                    FirstMonth = month;
                }
                if (SmsUtil.EXPENSE == transaction.getType()) {
                    totalExpenseOfMonth += transaction.getAmount();
                } else if (SmsUtil.INCOME == transaction.getType()) {
                    totalIncomeOfMonth += transaction.getAmount();
                }
            }
            expenseBarEntries.add(new BarEntry(month, totalExpenseOfMonth));
            incomeBarEntries.add(new BarEntry(month, totalIncomeOfMonth));
        }

        BarDataSet expenseDataSet = new BarDataSet(expenseBarEntries, getString(R.string.expense));
        expenseDataSet.setColor(ContextCompat.getColor(getActivity(), R.color.breakpoint_red));
        BarDataSet incomeDataSet = new BarDataSet(incomeBarEntries, getString(R.string.income));
        incomeDataSet.setColor(ContextCompat.getColor(getActivity(), android.R.color.holo_green_dark));

        initializeChart(incomeDataSet, expenseDataSet);
    }

    private void initializeChart(BarDataSet incomeDataSet, BarDataSet expenseDataSet) {
        BarData data = new BarData(incomeDataSet, expenseDataSet);
        float barWidth = 0.2f;
        data.setBarWidth(barWidth);
        mChart.setData(data);
        float groupSpace = 0.06f;
        float barSpace = 0.02f;

        if (FirstMonth >= 0) {
            mChart.groupBars(FirstMonth, groupSpace, barSpace);
        }

        mChart.getData().notifyDataChanged();
        mChart.notifyDataSetChanged();
    }

    public void addOrUpdateChart(){
        mChart.getData().notifyDataChanged();
        mChart.notifyDataSetChanged();
    }
}
