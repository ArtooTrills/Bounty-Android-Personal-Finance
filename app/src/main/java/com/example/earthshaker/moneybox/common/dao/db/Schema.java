package com.example.earthshaker.moneybox.common.dao.db;

/**
 * Created by earthshaker on 14/5/17.
 */

public class Schema {
    public static final String TEXT_TYPE = " TEXT";
    public static final String INTEGER_TYPE = " INTEGER";
    public static final String REAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";
    private static final String PRIMARY_KEY = " PRIMARY KEY";
    private static final String AUTOINCREMENT = " AUTOINCREMENT";
    private static final String UNIQUE = " UNIQUE";
    private static final String CREATE_TABLE = "CREATE TABLE ";
    public static final String SQL_CREATE_DATABASE_VERION_TABLE = CREATE_TABLE
            + Contract.DatabaseVersion.TABLE_NAME
            + "( "
            + Contract.DatabaseVersion._ID
            + INTEGER_TYPE
            + PRIMARY_KEY
            + AUTOINCREMENT
            + COMMA_SEP
            + Contract.DatabaseVersion.VERSION
            + TEXT_TYPE
            + ") ";

    public static final String SQL_CREATE_BUDGET = CREATE_TABLE
            + Contract.Budget.TABLE_NAME
            + " ("
            + Contract.Budget._ID
            + INTEGER_TYPE
            + PRIMARY_KEY
            + AUTOINCREMENT
            + COMMA_SEP
            + Contract.Budget.COLUMN_NAME_CATEGORY
            + TEXT_TYPE
            + COMMA_SEP
            + Contract.Budget.COLUMN_NAME_AMOUNT
            + REAL_TYPE
            + COMMA_SEP
            + Contract.Budget.COLUMN_NAME_CREATED_TIME
            + TEXT_TYPE
            + COMMA_SEP
            + Contract.Budget.COLUMN_NAME_LAST_UPDATED_TIME
            + TEXT_TYPE
            + COMMA_SEP +
            Contract.Budget.COLUMN_NAME_STATUS
            + TEXT_TYPE
            + COMMA_SEP
            + " )";

    public static final String SQL_CREATE_TRANSACTIONS = CREATE_TABLE
            + Contract.Transactions.TABLE_NAME
            + " ("
            + Contract.Transactions._ID
            + INTEGER_TYPE
            + PRIMARY_KEY
            + AUTOINCREMENT
            + COMMA_SEP
            + Contract.Transactions.COLUMN_NAME_AMOUNT
            + REAL_TYPE
            + COMMA_SEP
            + Contract.Transactions.COLUMN_NAME_EXPENSE_CATEGORY
            + TEXT_TYPE
            + COMMA_SEP
            + Contract.Transactions.COLLUMN_NAME_DATE
            + TEXT_TYPE
            + COMMA_SEP
            + Contract.Transactions.COLUMN_NAME_IS_EXPENSE
            + TEXT_TYPE
            + COMMA_SEP
            + Contract.Transactions.COLUMN_NAME_STATUS
            + TEXT_TYPE
            + COMMA_SEP
            + " )";

    public static final String SQL_CREATE_Messages = CREATE_TABLE
            + Contract.Messages.TABLE_NAME
            + " ("
            + Contract.Messages._ID
            + INTEGER_TYPE
            + PRIMARY_KEY
            + AUTOINCREMENT
            + COMMA_SEP
            + Contract.Messages.COLUMN_NAME_BODY
            + TEXT_TYPE
            + COMMA_SEP
            + Contract.Messages.COLUMN_NAME_SENDER
            + TEXT_TYPE
            + COMMA_SEP
            + Contract.Messages.COLUMN_NAME_TIME
            + INTEGER_TYPE
            + COMMA_SEP

            + " )";

}
