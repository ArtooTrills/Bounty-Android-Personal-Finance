package com.examples.ankit.breakpoint.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ankit on 21/05/17.
 */

public class Transactions {
    private List<Transaction> transactions;
    private long lastChecked;// used to stop SMS processing after this time.

    public List<Transaction> getTransactions() {
        if(transactions == null) {
            transactions = new ArrayList<>();
        }
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public long getLastChecked() {
        return lastChecked;
    }

    public void setLastChecked(long lastChecked) {
        this.lastChecked = lastChecked;
    }
}
