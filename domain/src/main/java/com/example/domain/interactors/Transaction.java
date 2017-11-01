package com.example.domain.interactors;

/**
 * Created by nazmuddinmavliwala on 01/11/2017.
 */

public class Transaction {

    private final long id;
    private final long amount;
    private final int transactionType;
    private final String expenseType;

    Transaction(long id, long amount, int transactionType, String expenseType) {
        this.id = id;
        this.amount = amount;
        this.transactionType = transactionType;
        this.expenseType = expenseType;
    }

    public long getId() {
        return id;
    }

    public long getAmount() {
        return amount;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public static class TransactionBuilder {

        private long id;
        private long expense;
        private int transactionType;
        private String expenseType;

        public TransactionBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public TransactionBuilder setExpense(long expense) {
            this.expense = expense;
            return this;
        }

        public TransactionBuilder setTransactionType(int transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public TransactionBuilder setExpenseType(String expenseType) {
            this.expenseType = expenseType;
            return this;
        }

        public Transaction createTransaction() {
            return new Transaction(id, expense, transactionType, expenseType);
        }
    }
}
