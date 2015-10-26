package com.manage.ak.moneyreport;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sms implements Serializable {

    private String msgType;
    private String msgAmt;
    private String msgDate;
    private String formatDate;
    private String msgBal;


    public Sms() {
    }

    public Sms(String msgType, String msgAmt, String msgDate, String msgBal) {
        this.msgType = msgType;
        this.msgAmt = msgAmt;
        this.msgDate = msgDate;
        this.msgBal = msgBal;
    }

    public String getMsgType() {
        return this.msgType;
    }

    public String getMsgAmt() {
        return this.msgAmt;
    }

    public Double getAmtDouble() {
        return Double.parseDouble(this.msgAmt);
    }

    public String getMsgDate() {
        return this.msgDate;
    }

    public String getMsgBal() {
        return this.msgBal;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public void setMsgAmt(String msgAmt) {
        this.msgAmt = msgAmt;
    }

    public void setMsgDate(String msgDate) {
        this.msgDate = msgDate;
    }

    public void setMsgBal(String msgBal) {
        this.msgBal = msgBal;
    }

    public long getDateLong() {
        return Long.parseLong(this.msgDate);
    }

    public String getFormatDate() {
        return getDate(Long.parseLong(this.msgDate));
    }

    public String getDay() {
        return new SimpleDateFormat("dd/MM").format(new Date(Long.parseLong(this.msgDate)));
    }

    public String getWeek() {
        return "Week:" + new SimpleDateFormat("W").format(new Date(Long.parseLong(this.msgDate))) + " of " + new SimpleDateFormat("MMM").format(new Date(Long.parseLong(this.msgDate)));
    }

    public String getMonth() {
        return new SimpleDateFormat("MMM").format(new Date(Long.parseLong(this.msgDate))) + "'" + new SimpleDateFormat("yy").format(new Date(Long.parseLong(this.msgDate)));
    }

    public String getDate(long milliSeconds) {
        // Create a DateFormatter object for displaying date in specified format.
        return new SimpleDateFormat("dd/MMM/yy").format(new Date(milliSeconds));
    }
}