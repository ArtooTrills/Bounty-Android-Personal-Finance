package com.example.earthshaker.moneybox.budget.dao;

import android.content.ContentValues;

import com.example.earthshaker.moneybox.budget.BudgetConfig;
import com.example.earthshaker.moneybox.common.dao.BaseDao;
import com.example.earthshaker.moneybox.common.dao.db.Contract;
import com.example.earthshaker.moneybox.common.dao.db.DatabaseProvider;
import com.example.earthshaker.moneybox.common.eventbus.CommonEvents;

import de.greenrobot.event.EventBus;

/**
 * Created by earthshaker on 14/5/17.
 */

public class BudgetModificationDao extends BaseDao {

    public static void saveBudget(BudgetConfig budgetConfig, boolean isEdit) {
        ContentValues cv = new ContentValues();

        cv.put(Contract.Budget.COLUMN_NAME_AMOUNT, budgetConfig.getTotalamount());
        cv.put(Contract.Budget.COLUMN_NAME_CATEGORY, budgetConfig.getCategory());
        if (isEdit) {
            String where = Contract.Budget.COLUMN_NAME_CATEGORY + " = '" + budgetConfig.getCategory() + "'";
            DatabaseProvider.provideDatabase().update(Contract.Budget.TABLE_NAME, cv, where, null);
            EventBus.getDefault().post(new CommonEvents.BudgetModifiedEvent());
        } else {
            DatabaseProvider.provideDatabase().insert(Contract.Budget.TABLE_NAME, null, cv);
            EventBus.getDefault().post(new CommonEvents.AddBudget());

        }
    }

    public static void deleteBudget(String category) {
        String where = Contract.Budget.COLUMN_NAME_CATEGORY + " = '" + category + "'";
        DatabaseProvider.provideDatabase().delete(Contract.Budget.TABLE_NAME, where, null);
        EventBus.getDefault().post(new CommonEvents.AddBudget());
    }
}
