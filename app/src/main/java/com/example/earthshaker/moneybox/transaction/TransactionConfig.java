package com.example.earthshaker.moneybox.transaction;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by earthshaker on 15/5/17.
 */

public class TransactionConfig implements Parcelable {

    private Boolean isExpense;
    private Double amount;
    private String date;
    private String category;
    private String id;

    public Boolean getExpense() {
        return isExpense;
    }

    public void setExpense(Boolean expense) {
        isExpense = expense;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.isExpense);
        dest.writeValue(this.amount);
        dest.writeString(this.date);
        dest.writeString(this.category);
        dest.writeString(this.id);
    }

    public TransactionConfig() {
    }

    protected TransactionConfig(Parcel in) {
        this.isExpense = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.amount = (Double) in.readValue(Double.class.getClassLoader());
        this.date = in.readString();
        this.category = in.readString();
        this.id = in.readString();
    }

    public static final Creator<TransactionConfig> CREATOR = new Creator<TransactionConfig>() {
        @Override
        public TransactionConfig createFromParcel(Parcel source) {
            return new TransactionConfig(source);
        }

        @Override
        public TransactionConfig[] newArray(int size) {
            return new TransactionConfig[size];
        }
    };
}
