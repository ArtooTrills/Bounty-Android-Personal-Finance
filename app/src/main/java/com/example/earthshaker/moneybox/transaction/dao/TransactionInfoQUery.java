package com.example.earthshaker.moneybox.transaction.dao;

import com.example.earthshaker.moneybox.common.dao.db.Contract;

/**
 * Created by earthshaker on 14/5/17.
 */

public class TransactionInfoQUery {

    public static String getExpenseAmount(String categoryName) {
        return "select " + Contract.Transactions.COLUMN_NAME_AMOUNT +
                " from " + Contract.Transactions.TABLE_NAME +
                " where " + Contract.Transactions.COLUMN_NAME_EXPENSE_CATEGORY +
                " = '" + categoryName + "' and " + Contract.Transactions.COLUMN_NAME_IS_EXPENSE + " = '1'";
    }

    public static String getNonExpenseAmount(String categoryName) {
        return "select " + Contract.Transactions.COLUMN_NAME_AMOUNT +
                " from " + Contract.Transactions.TABLE_NAME +
                " where " + Contract.Transactions.COLUMN_NAME_EXPENSE_CATEGORY +
                " = '" + categoryName + "' and " + Contract.Transactions.COLUMN_NAME_IS_EXPENSE + " != '1'";
    }

    public static String getTransactionLlist() {
        return "select * from " + Contract.Transactions.TABLE_NAME;
    }

    public static String getExpnse() {
        return "select " + Contract.Transactions.COLUMN_NAME_AMOUNT + " from " + Contract.Transactions.TABLE_NAME + " where " + Contract.Transactions.COLUMN_NAME_IS_EXPENSE + " = '1'";
    }
}
