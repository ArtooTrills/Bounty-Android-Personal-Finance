package com.examples.ankit.breakpoint.utils;

import com.examples.ankit.breakpoint.R;

/**
 * Created by ankit on 17/06/17.
 */

public class UiUtils {
    public static final int TRANSACTION_CATEGORY_NONE = 0;
    public static final int TRANSACTION_CATEGORY_GROCERY = 1;
    public static final int TRANSACTION_CATEGORY_FUEL = 2;
    public static final int TRANSACTION_CATEGORY_INVESTMENT = 3;
    public static final int TRANSACTION_CATEGORY_BILLS_AND_UTILITIES = 4;
    public static final int TRANSACTION_CATEGORY_TRAVEL = 5;
    public static final int TRANSACTION_CATEGORY_INSURANCE = 6;
    public static final int TRANSACTION_CATEGORY_LOAN = 7;
    public static final int TRANSACTION_CATEGORY_MOBILE_BILL = 8;
    public static final int TRANSACTION_CATEGORY_ELECTRICITY = 9;
    public static final int TRANSACTION_CATEGORY_OTHER = 10;

    public static int getCategoryicon(int categoryIndex) {
        switch (categoryIndex) {
            case TRANSACTION_CATEGORY_GROCERY: {
                return R.drawable.ic_groceries;
            }
            case TRANSACTION_CATEGORY_FUEL: {
                return R.drawable.ic_gas_station;
            }
            case TRANSACTION_CATEGORY_INVESTMENT: {
                return R.drawable.ic_investment;
            }
            case TRANSACTION_CATEGORY_BILLS_AND_UTILITIES: {
                return R.drawable.ic_utility_bill;
            }
            case TRANSACTION_CATEGORY_TRAVEL: {
                return R.drawable.ic_travel;
            }
            case TRANSACTION_CATEGORY_INSURANCE: {
                return R.drawable.ic_insurance;
            }
            case TRANSACTION_CATEGORY_LOAN: {
                return R.drawable.ic_loan;
            }
            case TRANSACTION_CATEGORY_MOBILE_BILL: {
                return R.drawable.ic_mobile;
            }
            case TRANSACTION_CATEGORY_ELECTRICITY: {
                return R.drawable.ic_electricity_bill;
            }
            case TRANSACTION_CATEGORY_OTHER: {
                return R.drawable.ic_other;
            }
            default: {
                return R.drawable.ic_other;
            }
        }
    }
}
