package com.example.nazmuddinmavliwala.ewallet.ui.transactions.presenter;

import com.example.nazmuddinmavliwala.ewallet.base.presenters.BasePresenter;
import com.example.nazmuddinmavliwala.ewallet.domain.model.Transactions;
import com.example.nazmuddinmavliwala.ewallet.ui.transactions.models.TransactionsData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by nazmuddinmavliwala on 20/10/2017.
 */

public class TransactionsPresenter extends BasePresenter<TransactionsView> {

    private TransactionUseCase useCase;
    private TransactionsDomainToVOConverter converter;

    @Inject
    public TransactionsPresenter(TransactionsView view,
                                 TransactionUseCase useCase,
                                 TransactionsDomainToVOConverter converter) {
        super(view);
        this.useCase = useCase;
        this.converter = converter;
    }

    public void fetchTransactions() {
        this.view.showLoading();
        this.useCase.fetchTransactions(new Subscriber<List<Transactions>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.hideLoading();
            }

            @Override
            public void onNext(List<Transactions> transactions) {
                view.hideLoading();
                if (transactions.size() > 0) {

                } else {
                    List<TransactionsData> transactionsData = new ArrayList<>();
                    for (Transactions transaction : transactions) {
                        transactionsData.add(converter.convert(transaction));
                    }
                    view.appendTransactions(transactionsData);
                }
            }
        });
    }
}
