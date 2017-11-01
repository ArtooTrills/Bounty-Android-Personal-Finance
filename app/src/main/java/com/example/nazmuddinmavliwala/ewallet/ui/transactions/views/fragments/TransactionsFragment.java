package com.example.nazmuddinmavliwala.ewallet.ui.transactions.views.fragments;

import android.view.View;

import com.example.nazmuddinmavliwala.ewallet.R;
import com.example.nazmuddinmavliwala.ewallet.app.di.WalletApp;
import com.example.nazmuddinmavliwala.ewallet.app.di.modules.RxModule;
import com.example.nazmuddinmavliwala.ewallet.base.views.SightFragment;
import com.example.nazmuddinmavliwala.ewallet.base.views.ViewManager;
import com.example.nazmuddinmavliwala.ewallet.ui.overview.di.ConverterModule;
import com.example.nazmuddinmavliwala.ewallet.ui.overview.views.viewdelegates.ErrorViewDelegate;
import com.example.nazmuddinmavliwala.ewallet.ui.overview.views.viewdelegates.LoaderViewDelegate;
import com.example.nazmuddinmavliwala.ewallet.ui.transactions.di.DaggerTransactionComponent;
import com.example.nazmuddinmavliwala.ewallet.ui.transactions.di.TransactionsModule;
import com.example.nazmuddinmavliwala.ewallet.ui.transactions.models.TransactionVO;
import com.example.nazmuddinmavliwala.ewallet.ui.transactions.models.TransactionsData;
import com.example.nazmuddinmavliwala.ewallet.ui.transactions.presenter.TransactionsPresenter;
import com.example.nazmuddinmavliwala.ewallet.ui.transactions.presenter.TransactionsView;
import com.example.nazmuddinmavliwala.ewallet.ui.transactions.views.viewdelegates.TransactionViewDelegate;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by nazmuddinmavliwala on 14/10/2017.
 */

public class TransactionsFragment extends SightFragment implements TransactionsView {

    @Inject
    ViewManager<List<TransactionVO>> viewManager;

    @Inject
    TransactionsPresenter presenter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_transactions;
    }

    @Override
    protected void onViewCreatedFirstSight(View view) {
        super.onViewCreatedFirstSight(view);
        DaggerTransactionComponent.builder()
                .applicationComponent(WalletApp.getComponent())
                .rxModule(new RxModule())
                .converterModule(new ConverterModule())
                .transactionsModule(new TransactionsModule(this))
                .build()
                .inject(this);
        this.viewManager.onCreate();
        this.presenter.fetchTransactions();
    }

    @Override
    public void showLoading() {
        this.viewManager.showExplicit(LoaderViewDelegate.class);
    }

    @Override
    public void hideLoading() {
        this.viewManager.hideSpecific(LoaderViewDelegate.class);
    }

    @Override
    public void showError() {
        this.viewManager.showExplicit(ErrorViewDelegate.class);
    }

    @Override
    public void showDataView() {
        this.viewManager.showExplicit(TransactionViewDelegate.class);
    }

    @Override
    public void bind(List<TransactionVO> transactionVOS) {
        TransactionViewDelegate delegate = this.viewManager.getViewDelegate(TransactionViewDelegate.class);
        delegate.bind(transactionVOS);
    }
}
