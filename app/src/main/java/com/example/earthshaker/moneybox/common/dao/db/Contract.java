package com.example.earthshaker.moneybox.common.dao.db;


/**
 * Created by earthshaker on 14/5/17.
 */

public final class Contract {
    public static abstract class Budget implements BaseContract {
        public static final String TABLE_NAME = "budget";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_AMOUNT = "amount";
    }

    public static abstract class Transactions implements BaseContract {
        public static final String TABLE_NAME = "transactions";
        public static final String COLUMN_NAME_EXPENSE_CATEGORY = "category";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLLUMN_NAME_DATE = "note";
        public static final String COLUMN_NAME_IS_EXPENSE = "isExpense";//credit or debit
    }

    public static abstract class Messages implements BaseContract {
        public static final String TABLE_NAME = "messages";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_SENDER = "sender";
        public static final String COLUMN_NAME_BODY = "body";
    }

    public static abstract class DatabaseVersion implements BaseContract {
        public static final String TABLE_NAME = "databaseVersion";
        public static final String VERSION = "version";
    }

}
