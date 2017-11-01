package com.example.domain.interactors;

import com.example.domain.base.UseCase;
import com.example.domain.executers.ExecutionThread;
import com.example.domain.executers.PostExecutionThread;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by nazmuddinmavliwala on 01/11/2017.
 */

public class TransactionUseCase extends UseCase<TransactionRepository> {

    @Inject
    public TransactionUseCase(ExecutionThread executionThread,
                              PostExecutionThread postExecutionThread,
                              TransactionRepository repository) {
        super(executionThread, postExecutionThread, repository);
    }

    public void fetchTransactions(Subscriber<List<Transaction>> subscriber) {
        this.repository.fetchTransactions()
                .compose(applySchedulers())
                .subscribe(subscriber);
    }
}
