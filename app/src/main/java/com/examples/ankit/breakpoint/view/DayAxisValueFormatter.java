package com.examples.ankit.breakpoint.view;

import com.examples.ankit.breakpoint.utils.DateUtil;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by ankit on 11/06/17.
 */

public class DayAxisValueFormatter implements IAxisValueFormatter {
    public DayAxisValueFormatter(BarLineChartBase<?> chart) {
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return DateUtil.getMonthName((int) value);
    }
}