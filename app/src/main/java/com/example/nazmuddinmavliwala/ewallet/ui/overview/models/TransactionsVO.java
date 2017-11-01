package com.example.nazmuddinmavliwala.ewallet.ui.overview.models;

/**
 * Created by nazmuddinmavliwala on 01/11/2017.
 */

public class TransactionsVO {
    private final long credit;
    private final long debit;

    public TransactionsVO(long credit, long debit) {

        this.credit = credit;
        this.debit = debit;
    }

    public long getCredit() {
        return credit;
    }

    public long getDebit() {
        return debit;
    }
}
