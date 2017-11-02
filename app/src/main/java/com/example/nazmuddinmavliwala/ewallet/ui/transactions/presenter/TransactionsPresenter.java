package com.example.nazmuddinmavliwala.ewallet.ui.transactions.presenter;

import com.example.domain.interactors.Transaction;
import com.example.domain.interactors.TransactionUseCase;
import com.example.nazmuddinmavliwala.ewallet.base.presenters.BasePresenter;
import com.example.nazmuddinmavliwala.ewallet.ui.transactions.models.TransactionVO;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by nazmuddinmavliwala on 20/10/2017.
 */

public class TransactionsPresenter extends BasePresenter<TransactionsView> {

    private final TransactionUseCase useCase;
    private final TransactionDomainVOConverter converter;

    @Inject
    public TransactionsPresenter(TransactionsView view,
                                 TransactionUseCase useCase,
                                 TransactionDomainVOConverter converter) {
        super(view);
        this.useCase = useCase;
        this.converter = converter;
    }

    public void fetchTransactions() {
        this.view.showLoading();
        this.useCase.fetchTransactions(new Subscriber<List<Transaction>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.hideLoading();
                view.showError();
            }

            @Override
            public void onNext(List<Transaction> transactions) {
                view.hideLoading();
                view.showDataView();
                List<TransactionVO> transactionVOS = new ArrayList<>();
                for (Transaction transaction : transactions) {
                    transactionVOS.add(converter.convert(transaction));
                }
                view.bind(transactionVOS);
            }
        });
    }
}
