package com.manage.ak.moneyreport;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

public class TransFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trans_fragment, container, false);

        List<Sms> spendList = (ArrayList<Sms>) getArguments().getSerializable("SPEND LIST");

        int length = spendList.size();

        if (length > 5)
            length = 5;

        double[] expenses = new double[length];
        String[] time = new String[length];
        for (int z = 0; z < length; z++) {
            expenses[z] = spendList.get(z).getAmtDouble();
            time[z] = spendList.get(z).getTime();
        }

        double max = 0;
        DataPoint[] dataPoints = new DataPoint[length];
        for (int k = 0; k < length; k++) {
            dataPoints[k] = new DataPoint(k, expenses[k]);
            if (expenses[k] > max) {
                max = expenses[k];
            }
        }

        GraphView graph = (GraphView) view.findViewById(R.id.dailyReport);
        graph.removeAllSeries();

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
        graph.addSeries(series);
        series.setColor(Color.WHITE);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(length - 1);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(max);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);

        graph.setBackgroundColor(Color.TRANSPARENT);
        graph.getGridLabelRenderer().setVerticalLabelsVisible(true);

        if (length > 1) {
            graph.getGridLabelRenderer().setHorizontalLabelsVisible(true);
            StaticLabelsFormatter s = new StaticLabelsFormatter(graph);
            s.setHorizontalLabels(time);
            graph.getGridLabelRenderer().setLabelFormatter(s);
        }

        return view;
    }
}