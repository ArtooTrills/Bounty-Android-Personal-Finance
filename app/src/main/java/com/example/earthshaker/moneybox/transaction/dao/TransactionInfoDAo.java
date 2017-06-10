package com.example.earthshaker.moneybox.transaction.dao;

import com.example.earthshaker.moneybox.common.dao.BaseDao;
import com.example.earthshaker.moneybox.common.dao.db.Contract;
import com.example.earthshaker.moneybox.transaction.TransactionConfig;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

/**
 * Created by earthshaker on 14/5/17.
 */

public class TransactionInfoDAo extends BaseDao {

    public static Double getAmountFromCategory(String category) {

        String expenseQuery = TransactionInfoQUery.getExpenseAmount(category);
        List<Double> amounts = new ArrayList<>();

        runRawQuery(expenseQuery, c -> amounts.add(0, c.getDouble(0)));
        Double expenseAmount = 0d;
        for(Double amount:amounts){
            expenseAmount+=amount;
        }

        return expenseAmount;
    }

    public static Double getTotalExpense() {
        String query = TransactionInfoQUery.getExpnse();
        List<Double> amounts = new ArrayList<>();
        runRawQuery(query, c -> amounts.add(c.getDouble(0)));
        Double totalAmount = 0d;
        for (Double amount : amounts) {
            totalAmount += amount;
        }
        return totalAmount;
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
