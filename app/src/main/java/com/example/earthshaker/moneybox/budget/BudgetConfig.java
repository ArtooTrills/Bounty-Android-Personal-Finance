package com.example.earthshaker.moneybox.budget;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by earthshaker on 14/5/17.
 */

public class BudgetConfig implements Parcelable {

    double totalamount;
    double spent;
    String category;
    String id;

    public double getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(double totalamount) {
        this.totalamount = totalamount;
    }

    public double getSpent() {
        return spent;
    }

    public void setSpent(double spent) {
        this.spent = spent;
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
        dest.writeDouble(this.totalamount);
        dest.writeDouble(this.spent);
        dest.writeString(this.category);
        dest.writeString(this.id);
    }

    public BudgetConfig() {
    }

    protected BudgetConfig(Parcel in) {
        this.totalamount = in.readDouble();
        this.spent = in.readDouble();
        this.category = in.readString();
        this.id = in.readString();
    }

    public static final Creator<BudgetConfig> CREATOR = new Creator<BudgetConfig>() {
        @Override
        public BudgetConfig createFromParcel(Parcel source) {
            return new BudgetConfig(source);
        }

        @Override
        public BudgetConfig[] newArray(int size) {
            return new BudgetConfig[size];
        }
    };
}
