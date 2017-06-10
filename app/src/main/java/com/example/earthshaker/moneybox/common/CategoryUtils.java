package com.example.earthshaker.moneybox.common;

import com.example.earthshaker.moneybox.R;

/**
 * Created by earthshaker on 14/5/17.
 */

public class CategoryUtils {

    public static int getCategoryIcon(String categoryName) {
        switch (categoryName) {
            case "Automobile":
                return R.drawable.exp_transport;
            case "Travel":
                return R.drawable.exp_travel;
            case "Food":
                return R.drawable.exp_food;
            case "Personal":
                return R.drawable.exp_personal;
            case "Shopping":
                return R.drawable.exp_shopping;
            case "Salon":
                return R.drawable.exp_salon;
            default:
                return R.drawable.exp_others;
        }
    }
}
