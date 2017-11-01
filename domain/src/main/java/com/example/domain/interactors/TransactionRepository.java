package com.example.domain.interactors;

import com.example.domain.base.Repository;

import java.util.List;

import rx.Observable;

/**
 * Created by nazmuddinmavliwala on 01/11/2017.
 */

public interface TransactionRepository extends Repository {
    Observable<List<Transaction>> fetchTransactions();
}
