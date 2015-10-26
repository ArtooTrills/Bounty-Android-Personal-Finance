package com.manage.ak.moneyreport;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportItem {

    private String reportType;
    private String reportAmt;
    private Long longTime;
    private String dayType;

    public ReportItem(String reportType, String reportAmt, Long longTime, String dayType) {
        this.reportType = reportType;
        this.reportAmt = reportAmt;
        this.longTime = longTime;
        this.dayType = dayType;
    }

    public String getDayType() {
        return this.dayType;
    }

    public String getReportType() {
        return this.reportType;
    }

    public String getReportAmt() {
        return this.reportAmt;
    }

    public String getReportTime() {
        return new SimpleDateFormat("HH:mm").format(new Date(this.longTime));
    }

    public String getReportDayTime() {
        return new SimpleDateFormat("cccc HH:mm").format(new Date(this.longTime));
    }

    public String getReportDateTime(){
        return new SimpleDateFormat("d/MMM/yyyy HH:mm").format(new Date(this.longTime));
    }

    public String getReportDate() {
        return new SimpleDateFormat("d/MMM/yyyy").format(new Date(this.longTime));
    }

    public String getReportWeek() {
        return "Week-" + new SimpleDateFormat("W").format(new Date(this.longTime)) + " of " + new SimpleDateFormat("MMM").format(new Date(this.longTime));
    }

    public String getReportMonth() {
        return new SimpleDateFormat("MMMM").format(new Date(this.longTime));
    }
}
