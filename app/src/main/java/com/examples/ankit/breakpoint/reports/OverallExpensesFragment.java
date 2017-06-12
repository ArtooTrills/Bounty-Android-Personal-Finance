package com.examples.ankit.breakpoint.reports;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.examples.ankit.breakpoint.R;
import com.examples.ankit.breakpoint.models.Transaction;
import com.examples.ankit.breakpoint.models.Transactions;
import com.examples.ankit.breakpoint.prefences.MyPreferenceManager;
import com.examples.ankit.breakpoint.sms.SmsUtil;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OverallExpensesFragment extends Fragment implements OnChartValueSelectedListener {

    private OnOverallExpenseClickListener mListener;

    @BindView(R.id.expenses_summary_chart)
    PieChart mChart;

    public OverallExpensesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializePieChartProperties();
        addOrUpdateChart();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnOverallExpenseClickListener) {
            mListener = (OnOverallExpenseClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAddExpenseListener");
        }
    }

    public void addOrUpdateChart() {
        Transactions transactions = MyPreferenceManager.getTransactions();
        if (transactions != null) {
            setOverallData(getExpenseOverview(transactions));
        }
    }

    private void initializePieChartProperties() {
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setCenterText(getString(R.string.overview));

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        Legend legend = mChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setXEntrySpace(7f);
        legend.setYEntrySpace(0f);
        legend.setYOffset(0f);
        legend.setEnabled(true);

        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTextSize(12f);
        mChart.setOnChartValueSelectedListener(this);
    }

    private Float[] getExpenseOverview(Transactions transactions) {
        if (transactions == null ||
                (transactions.getTransactions() == null && transactions.getTransactions().isEmpty())) {
            return new Float[]{};
        }
        List<Transaction> transactionList = transactions.getTransactions();
        float expenses = 0;
        float income = 0;
        for (Transaction transaction : transactionList) {
            if (SmsUtil.EXPENSE == transaction.getType()) {
                expenses += transaction.getAmount();
            }

            if (SmsUtil.INCOME == transaction.getType()) {
                income += transaction.getAmount();
            }

        }
        return new Float[]{expenses, income};
    }

    private void setOverallData(Float[] data) {
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        for (int i = 0; i < data.length; i++) {
            PieEntry entry = new PieEntry(data[i]);
            if (i == 0) {
                entry.setLabel(getString(R.string.expense));

            } else {
                entry.setLabel(getString(R.string.income));

            }
            entries.add(entry);

        }

        PieDataSet dataSet = new PieDataSet(entries, getString(R.string.overview));

        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(10f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(ContextCompat.getColor(getActivity(), R.color.breakpoint_red));
        colors.add(ContextCompat.getColor(getActivity(), android.R.color.holo_green_dark));
        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.WHITE);
        mChart.setData(pieData);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null) {
            return;
        }
        if (mListener != null) {
            mListener.onExpenseClick((int) h.getX());
        }
    }

    @Override
    public void onNothingSelected() {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnOverallExpenseClickListener {
        // TODO: Update argument type and name
        void onExpenseClick(int type);
    }
}
