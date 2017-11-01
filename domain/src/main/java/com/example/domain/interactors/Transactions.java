package com.example.domain.interactors;

import java.util.List;

/**
 * Created by nazmuddinmavliwala on 01/11/2017.
 */

public class Transactions {

    private final long credit;
    private final long debit;

    public Transactions(long credit, long debit) {
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
