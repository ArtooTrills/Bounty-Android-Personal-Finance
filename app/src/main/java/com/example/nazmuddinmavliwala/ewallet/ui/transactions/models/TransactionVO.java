package com.example.nazmuddinmavliwala.ewallet.ui.transactions.models;

/**
 * Created by nazmuddinmavliwala on 01/11/2017.
 */

public class TransactionVO {


    private final long id;
    private final long amount;
    private final int transactionType;
    private final String expenseType;

    TransactionVO(long id, long amount, int transactionType, String expenseType) {
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

    public static class TransactionVOBuilder {

        private long id;
        private long amount;
        private int transactionType;
        private String expenseType;

        public TransactionVOBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public TransactionVOBuilder setAmount(long amount) {
            this.amount = amount;
            return this;
        }

        public TransactionVOBuilder setTransactionType(int transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public TransactionVOBuilder setExpenseType(String expenseType) {
            this.expenseType = expenseType;
            return this;
        }

        public TransactionVO createTransactionVO() {
            return new TransactionVO(id, amount, transactionType, expenseType);
        }
    }
}
