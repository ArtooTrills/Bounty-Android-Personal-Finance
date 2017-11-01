package com.example.domain.interactors;

import com.example.domain.base.UseCase;
import com.example.domain.executers.ExecutionThread;
import com.example.domain.executers.PostExecutionThread;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by nazmuddinmavliwala on 01/11/2017.
 */

public class OverviewUseCase extends UseCase<OverviewRepository> {

    @Inject
    public OverviewUseCase(ExecutionThread executionThread,
                           PostExecutionThread postExecutionThread,
                           OverviewRepository repository) {
        super(executionThread, postExecutionThread, repository);
    }

    public void fetchTransactions(Subscriber<Transactions> subscriber) {
        this.repository.fetchTransactions()
                .compose(applySchedulers())
                .switchMap(transactions -> {
                    long credit  = 0;
                    long debit = 0;
                    for (Transaction transaction : transactions) {
                        int type = transaction.getTransactionType();
                        if (type == 0) credit = credit + transaction.getAmount();
                        else debit = debit + transaction.getAmount();
                    }
                    return Observable.just(new Transactions(credit,debit))
                            .compose(applySchedulers());

                })
                .subscribe(subscriber);
    }
}
