package com.example.earthshaker.moneybox.transaction.dao;

import android.content.ContentValues;
import android.util.EventLog;

import com.example.earthshaker.moneybox.common.dao.BaseDao;
import com.example.earthshaker.moneybox.common.dao.db.Contract;
import com.example.earthshaker.moneybox.common.dao.db.DatabaseProvider;
import com.example.earthshaker.moneybox.common.eventbus.CommonEvents;
import com.example.earthshaker.moneybox.transaction.TransactionConfig;

import de.greenrobot.event.EventBus;

/**
 * Created by earthshaker on 15/5/17.
 */

public class TransactionModificationDao extends BaseDao {

    public static void saveTransaction(TransactionConfig transactionConfig) {
        ContentValues cv = TransactionOrm.getContentValues(transactionConfig);
        DatabaseProvider.provideDatabase().insert(Contract.Transactions.TABLE_NAME, null, cv);
        EventBus.getDefault().post(new CommonEvents.AddTransaction());
        EventBus.getDefault().post(new CommonEvents.BudgetModifiedEvent());
    }

    public static void editTransaction(TransactionConfig transactionConfig) {
        ContentValues cv = TransactionOrm.getContentValues(transactionConfig);
        String where = Contract.Transactions._ID + " = '" + transactionConfig.getId() + "'";
        DatabaseProvider.provideDatabase().update(Contract.Transactions.TABLE_NAME, cv, where, null);
        EventBus.getDefault().post(new CommonEvents.AddTransaction());
        EventBus.getDefault().post(new CommonEvents.BudgetModifiedEvent());
    }

    public static void deleteTransaction(String id) {
        String where = Contract.Transactions._ID + " = '" + id + "'";
        DatabaseProvider.provideDatabase().delete(Contract.Transactions.TABLE_NAME, where, null);
        EventBus.getDefault().post(new CommonEvents.AddTransaction());
        EventBus.getDefault().post(new CommonEvents.BudgetModifiedEvent());
    }
}
