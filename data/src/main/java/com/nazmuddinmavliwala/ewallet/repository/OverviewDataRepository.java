package com.nazmuddinmavliwala.ewallet.repository;

import com.example.domain.interactors.OverviewRepository;
import com.example.domain.interactors.Transaction;
import com.nazmuddinmavliwala.ewallet.database.entities.DaoSession;
import com.nazmuddinmavliwala.ewallet.database.entities.TransactionEntity;
import com.nazmuddinmavliwala.ewallet.network.NetworkService;
import com.nazmuddinmavliwala.ewallet.network.TransactionDO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by nazmuddinmavliwala on 01/11/2017.
 */

public class OverviewDataRepository implements OverviewRepository {

    private final DaoSession daoSession;
    private final NetworkService service;
    private final TransactionEntityDOConverter entityDOConverter;
    private final TransactionEntityDomainConverter entityDomainConverter;

    @Inject
    public OverviewDataRepository(DaoSession daoSession,
                                  NetworkService service,
                                  TransactionEntityDOConverter entityDOConverter,
                                  TransactionEntityDomainConverter entityDomainConverter) {
        this.daoSession = daoSession;
        this.service = service;
        this.entityDOConverter = entityDOConverter;
        this.entityDomainConverter = entityDomainConverter;
    }

    @Override
    public Observable<List<Transaction>> fetchTransactions() {
        long count = this.daoSession.getTransactionEntityDao().count();
        if (count == 0) {
              return this.service.getTransactions()
                      .delay(2, TimeUnit.SECONDS)
                      .doOnNext(this::cache)
                      .switchMap(transactionDOS -> this.daoSession.getTransactionEntityDao().rx()
                              .loadAll()
                              .map(transactionEntities -> {
                                  List<Transaction> transactions = new ArrayList<>();
                                  for (TransactionEntity transactionEntity : transactionEntities) {
                                      transactions.add(entityDomainConverter.convert(transactionEntity));
                                  }
                                  return transactions;
                              }));
        } else {
            return this.daoSession.getTransactionEntityDao().rx()
                    .loadAll()
                    .delay(2,TimeUnit.SECONDS)
                    .switchMap(transactionEntities -> {
                        List<Transaction> transactions = new ArrayList<>();
                        for (TransactionEntity transactionEntity : transactionEntities) {
                            transactions.add(entityDomainConverter.convert(transactionEntity));
                        }
                        return Observable.just(transactions);
                        }
                    );
        }
    }

    private void cache(List<TransactionDO> transactionDOS) {
        for (TransactionDO transactionDO : transactionDOS) {
            TransactionEntity entity = entityDOConverter.convert(transactionDO);
            this.daoSession.getTransactionEntityDao().insertOrReplace(entity);
        }
    }
}
