package com.example.earthshaker.moneybox.budget.dao;

import android.database.Cursor;

import com.example.earthshaker.moneybox.budget.BudgetConfig;
import com.example.earthshaker.moneybox.common.dao.db.Contract;

/**
 * Created by earthshaker on 14/5/17.
 */

public class BudgetOrm {

    public static BudgetConfig getBudgetConfig(Cursor cursor) {
        BudgetConfig budgetConfig = new BudgetConfig();
        budgetConfig.setCategory(cursor.getString(cursor.getColumnIndex(Contract.Budget.COLUMN_NAME_CATEGORY)));
        budgetConfig.setTotalamount(cursor.getDouble(cursor.getColumnIndex(Contract.Budget.COLUMN_NAME_AMOUNT)));
        budgetConfig.setId(cursor.getString(cursor.getColumnIndex(Contract.Budget._ID)));
        return budgetConfig;
    }
}
