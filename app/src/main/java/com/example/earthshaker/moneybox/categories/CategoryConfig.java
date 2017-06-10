package com.example.earthshaker.moneybox.categories;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by earthshaker on 14/5/17.
 */

public class CategoryConfig implements Parcelable {

    String categoryName;
    Integer categoryIcons;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryIcons() {
        return categoryIcons;
    }

    public void setCategoryIcons(Integer categoryIcons) {
        this.categoryIcons = categoryIcons;
    }

    public static Creator<CategoryConfig> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.categoryName);
        dest.writeValue(this.categoryIcons);
    }

    public CategoryConfig() {
    }

    protected CategoryConfig(Parcel in) {
        this.categoryName = in.readString();
        this.categoryIcons = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<CategoryConfig> CREATOR = new Parcelable.Creator<CategoryConfig>() {
        @Override
        public CategoryConfig createFromParcel(Parcel source) {
            return new CategoryConfig(source);
        }

        @Override
        public CategoryConfig[] newArray(int size) {
            return new CategoryConfig[size];
        }
    };
}
