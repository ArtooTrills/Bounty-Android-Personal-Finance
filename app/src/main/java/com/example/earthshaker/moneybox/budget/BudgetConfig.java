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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.totalamount);
        dest.writeDouble(this.spent);
        dest.writeString(this.category);
    }

    public BudgetConfig() {
    }

    protected BudgetConfig(Parcel in) {
        this.totalamount = in.readDouble();
        this.spent = in.readDouble();
        this.category = in.readString();
    }

    public static final Parcelable.Creator<BudgetConfig> CREATOR = new Parcelable.Creator<BudgetConfig>() {
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
