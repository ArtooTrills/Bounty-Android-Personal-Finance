package com.example.nazmuddinmavliwala.ewallet.ui.overview.views.fragments;

import android.view.View;

import com.example.domain.interactors.Transactions;
import com.example.nazmuddinmavliwala.ewallet.R;
import com.example.nazmuddinmavliwala.ewallet.app.di.WalletApp;
import com.example.nazmuddinmavliwala.ewallet.app.di.modules.RxModule;
import com.example.nazmuddinmavliwala.ewallet.base.views.SightFragment;
import com.example.nazmuddinmavliwala.ewallet.base.views.ViewManager;
import com.example.nazmuddinmavliwala.ewallet.ui.overview.di.ConverterModule;
import com.example.nazmuddinmavliwala.ewallet.ui.overview.di.DaggerOverviewComponent;
import com.example.nazmuddinmavliwala.ewallet.ui.overview.di.OverviewModule;
import com.example.nazmuddinmavliwala.ewallet.ui.overview.models.TransactionsVO;
import com.example.nazmuddinmavliwala.ewallet.ui.overview.presenters.OverviewPresenter;
import com.example.nazmuddinmavliwala.ewallet.ui.overview.presenters.OverviewView;
import com.example.nazmuddinmavliwala.ewallet.ui.overview.views.viewdelegates.ErrorViewDelegate;
import com.example.nazmuddinmavliwala.ewallet.ui.overview.views.viewdelegates.LoaderViewDelegate;
import com.example.nazmuddinmavliwala.ewallet.ui.overview.views.viewdelegates.OverviewViewDelegate;

import javax.inject.Inject;

/**
 * Created by nazmuddinmavliwala on 14/10/2017.
 */

public class OverviewFragment extends SightFragment implements OverviewView {

    @Inject
    ViewManager<TransactionsVO> viewManager;

    @Inject
    OverviewPresenter presenter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_overview;
    }

    @Override
    protected void onViewCreatedFirstSight(View view) {
        super.onViewCreatedFirstSight(view);
        DaggerOverviewComponent
                .builder()
                .applicationComponent(WalletApp.getComponent())
                .converterModule(new ConverterModule())
                .overviewModule(new OverviewModule(this))
                .rxModule(new RxModule())
                .build()
                .inject(this);
        this.presenter.fetchTransactions();
    }

    @Override
    public void showErrorView() {
        this.viewManager.showExplicit(ErrorViewDelegate.class);
    }

    @Override
    public void showDataView() {
        this.viewManager.showExplicit(OverviewViewDelegate.class);
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
    public void bind(TransactionsVO transactions) {
        OverviewViewDelegate viewDelegate = this.viewManager.getViewDelegate(OverviewViewDelegate.class);
        viewDelegate.bind(transactions);
    }
}
