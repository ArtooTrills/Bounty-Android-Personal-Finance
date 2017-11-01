package com.example.nazmuddinmavliwala.ewallet.ui.overview.presenters;

import com.example.domain.interactors.OverviewUseCase;
import com.example.domain.interactors.Transactions;
import com.example.nazmuddinmavliwala.ewallet.base.presenters.BasePresenter;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by nazmuddinmavliwala on 20/10/2017.
 */

public class OverviewPresenter extends BasePresenter<OverviewView> {

    private final OverviewUseCase useCase;
    private final TransactionsDomainVoConverter converter;

    @Inject
    public OverviewPresenter(OverviewView view,
                             OverviewUseCase useCase,
                             TransactionsDomainVoConverter converter) {
        super(view);
        this.useCase = useCase;
        this.converter = converter;
    }

    public void fetchTransactions() {
        view.showLoading();
        this.useCase.fetchTransactions(new Subscriber<Transactions>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.hideLoading();
                view.showErrorView();
            }

            @Override
            public void onNext(Transactions transactions) {
                view.showDataView();
                view.bind(converter.convert(transactions));
            }
        });
    }
}
