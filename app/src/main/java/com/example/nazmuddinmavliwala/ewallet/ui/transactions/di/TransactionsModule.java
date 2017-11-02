package com.example.nazmuddinmavliwala.ewallet.ui.transactions.di;

import android.content.Context;
import android.view.View;

import com.example.domain.interactors.TransactionRepository;
import com.example.nazmuddinmavliwala.ewallet.R;
import com.example.nazmuddinmavliwala.ewallet.app.di.identifiers.ScopedActivity;
import com.example.nazmuddinmavliwala.ewallet.base.views.ViewDelegate;
import com.example.nazmuddinmavliwala.ewallet.base.views.ViewManager;
import com.example.nazmuddinmavliwala.ewallet.ui.overview.views.viewdelegates.ErrorViewDelegate;
import com.example.nazmuddinmavliwala.ewallet.ui.overview.views.viewdelegates.LoaderViewDelegate;
import com.example.nazmuddinmavliwala.ewallet.ui.transactions.models.TransactionVO;
import com.example.nazmuddinmavliwala.ewallet.ui.transactions.presenter.TransactionsView;
import com.example.nazmuddinmavliwala.ewallet.ui.transactions.views.fragments.TransactionsFragment;
import com.example.nazmuddinmavliwala.ewallet.ui.transactions.views.viewdelegates.TransactionViewDelegate;
import com.nazmuddinmavliwala.ewallet.repository.TransactionDatarepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nazmuddinmavliwala on 01/11/2017.
 */


@ScopedActivity
@Module
public class TransactionsModule {

    private TransactionsFragment fragment;

    public TransactionsModule(TransactionsFragment fragment) {
        this.fragment = fragment;
    }

    @ScopedActivity
    @Provides
    public TransactionsView provideView() {
        return this.fragment;
    }

    @ScopedActivity
    @Provides
    public TransactionRepository provideRepo(TransactionDatarepository datarepository) {
        return datarepository;
    }


    @ScopedActivity
    @Provides
    @Named("Activity")
    public Context provideContext() {
        return this.fragment.getActivity();
    }

    @ScopedActivity
    @Provides
    @Named("Data")
    public View provideDataView() {
        return this.fragment.findView(R.id.v_data);
    }

    @ScopedActivity
    @Provides
    @Named("Error")
    public View provideError() {
        return this.fragment.findView(R.id.v_error);
    }

    @ScopedActivity
    @Provides
    @Named("Loader")
    public View provideLoader() {
        return this.fragment.findView(R.id.v_loader);
    }

    @ScopedActivity
    @Provides
    public ViewManager<List<TransactionVO>> provideViewManager(ErrorViewDelegate errorViewDelegate,
                                                               LoaderViewDelegate loaderViewDelegate,
                                                               TransactionViewDelegate transactionViewDelegate) {
        List<ViewDelegate<List<TransactionVO>>> viewDelegates = new ArrayList<>();
        viewDelegates.add(errorViewDelegate);
        viewDelegates.add(loaderViewDelegate);
        viewDelegates.add(transactionViewDelegate);
        return new ViewManager<>(viewDelegates);
    }

}
