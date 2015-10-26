package com.manage.ak.moneyreport;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    private Context context;
    List<ReportItem> reportList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView reportType;
        public TextView reportAmt;
        public TextView reportTime;

        public ViewHolder(View v) {
            super(v);
            reportType = (TextView) v.findViewById(R.id.reportType);
            reportAmt = (TextView) v.findViewById(R.id.reportAmt);
            reportTime = (TextView) v.findViewById(R.id.reportTime);
        }
    }

    public ReportAdapter(List<ReportItem> reportList, Context context) {
        this.reportList = reportList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.reportType.setText(reportList.get(position).getReportType());
        holder.reportAmt.setText("₹ " + reportList.get(position).getReportAmt());
        holder.reportAmt.setTextColor(Color.RED);
        if (reportList.get(0).getDayType().equals("Daily"))
            holder.reportTime.setText(reportList.get(position).getReportTime());
        else if (reportList.get(0).getDayType().equals("Weekly"))
            holder.reportTime.setText(reportList.get(position).getReportDayTime());
        else if (reportList.get(0).getDayType().equals("Monthly"))
            holder.reportTime.setText(reportList.get(position).getReportDateTime());
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }
}
