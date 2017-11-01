package com.nazmuddinmavliwala.ewallet.repository;

import com.example.domain.interactors.Transaction;
import com.example.domain.interactors.TransactionRepository;
import com.nazmuddinmavliwala.ewallet.database.entities.DaoSession;
import com.nazmuddinmavliwala.ewallet.database.entities.TransactionEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by nazmuddinmavliwala on 01/11/2017.
 */

public class TransactionDatarepository implements TransactionRepository {

    private DaoSession daoSession;
    private TransactionEntityDomainConverter converter;

    @Inject
    public TransactionDatarepository(DaoSession daoSession,
                                     TransactionEntityDomainConverter converter) {
        this.daoSession = daoSession;
        this.converter = converter;
    }

    @Override
    public Observable<List<Transaction>> fetchTransactions() {
        return this.daoSession.getTransactionEntityDao().rx()
                .loadAll()
                .delay(2, TimeUnit.SECONDS)
                .switchMap(transactionEntities -> {
                    List<Transaction> transactions = new ArrayList<>();
                    for (TransactionEntity transactionEntity :transactionEntities) {
                        transactions.add(this.converter.convert(transactionEntity));
                    }
                    return Observable.just(transactions);
                });
    }
}
