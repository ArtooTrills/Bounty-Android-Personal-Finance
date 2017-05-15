package com.example.earthshaker.moneybox.transaction.dao;

import com.example.earthshaker.moneybox.common.dao.BaseDao;
import com.example.earthshaker.moneybox.common.dao.db.Contract;
import com.example.earthshaker.moneybox.transaction.TransactionConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by earthshaker on 14/5/17.
 */

public class TransactionInfoDAo extends BaseDao {

    public static Double getAmountFromCategory(String category) {
        return getFirstRecord(TransactionInfoQUery.getAmount(category), cursor -> cursor.getDouble(
                cursor.getColumnIndex(Contract.Transactions.COLUMN_NAME_AMOUNT)));

    }

    public static List<TransactionConfig> getTransactionList() {
        List<TransactionConfig> transactionConfigs = new ArrayList<>();
        String query = TransactionInfoQUery.getTransactionLlist();
        runRawQuery(query, cursor -> transactionConfigs.add(TransactionOrm.getTransactionCOnfig(cursor)));
        return transactionConfigs;
    }

    public static List<TransactionConfig> fetchTopTwoTxns() {
        List<TransactionConfig> transactionConfigs = new ArrayList<>();
        String query = "select * from " + Contract.Transactions.TABLE_NAME + " ORDER BY " + Contract.Transactions.COLUMN_NAME_AMOUNT + " DESC LIMIT 2";
        runRawQuery(query, cursor -> transactionConfigs.add(TransactionOrm.getTransactionCOnfig(cursor)));
        return transactionConfigs;
    }
}
