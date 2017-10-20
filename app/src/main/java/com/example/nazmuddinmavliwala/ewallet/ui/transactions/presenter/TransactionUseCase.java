package com.example.nazmuddinmavliwala.ewallet.ui.transactions.presenter;

import com.example.nazmuddinmavliwala.ewallet.base.presenters.UseCase;
import com.example.nazmuddinmavliwala.ewallet.domain.executers.ExecutionThread;
import com.example.nazmuddinmavliwala.ewallet.domain.executers.PostExecutionThread;
import com.example.nazmuddinmavliwala.ewallet.domain.model.Transactions;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by nazmuddinmavliwala on 20/10/2017.
 */

public class TransactionUseCase extends UseCase<TransactionsRepository> {

    @Inject
    public TransactionUseCase(ExecutionThread executionThread,
                              PostExecutionThread postExecutionThread,
                              TransactionsRepository repository) {
        super(executionThread, postExecutionThread, repository);
    }

    public void fetchTransactions(Subscriber<List<Transactions>> subscriber) {
        this.repository.fetchTransactions()
                .compose(applySchedulers())
                .subscribe(subscriber);

    }
}
