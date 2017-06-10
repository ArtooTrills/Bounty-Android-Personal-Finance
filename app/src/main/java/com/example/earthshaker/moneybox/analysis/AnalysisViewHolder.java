package com.example.earthshaker.moneybox.analysis;

import android.graphics.Color;
import android.view.View;

import com.example.earthshaker.moneybox.R;
import com.example.earthshaker.moneybox.categories.CategoryConfig;
import com.example.earthshaker.moneybox.categories.CategoryUtils;
import com.example.earthshaker.moneybox.transaction.TransactionConfig;
import com.example.earthshaker.moneybox.transaction.dao.TransactionInfoDAo;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by earthshaker on 15/5/17.
 */

public class AnalysisViewHolder {

    private BarChart chart;

    public AnalysisViewHolder(AnalysisActivity analysisActivity, View view) {
        chart = (BarChart) view.findViewById(R.id.chart);

        BarData data = new BarData(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.setDescription("My Chart");
        chart.animateXY(2000, 2000);
        chart.invalidate();
    }


    private ArrayList<BarDataSet> getDataSet() {

        float food = 0F, other = 0F, personal = 0F, salon = 0F, shopping = 0F, transport = 0F;

        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();

        List<TransactionConfig> transactionConfigs = TransactionInfoDAo.getTransactionList();
        for (TransactionConfig transactionConfig : transactionConfigs) {
            switch (transactionConfig.getCategory()) {
                case "Food":
                    food += transactionConfig.getAmount();
                case "Other":
                    other += transactionConfig.getAmount();
                case "Personal":
                    personal += transactionConfig.getAmount();
                case "Salon":
                    salon += transactionConfig.getAmount();
                case "Shopping":
                    shopping += transactionConfig.getAmount();
                case "Transport":
                    transport += transactionConfig.getAmount();
                default:
                    other += transactionConfig.getAmount();
            }
        }
        BarEntry v1e1 = new BarEntry(food, 0); // Jan
        valueSet1.add(v1e1);

        BarEntry v1e2 = new BarEntry(other, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(personal, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(salon, 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(shopping, 4); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(transport, 5); // Jun
        valueSet1.add(v1e6);


        BarDataSet barDataSet2 = new BarDataSet(valueSet1, "Brand 2");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet2);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        List<CategoryConfig> categoryConfigList = CategoryUtils.getCategoryConfigs();
        ArrayList<String> xAxis = new ArrayList<>();
        for (CategoryConfig category : categoryConfigList) {
            xAxis.add(category.getCategoryName());
        }
        return xAxis;
    }
}
