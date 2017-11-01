package com.example.nazmuddinmavliwala.ewallet.ui.overview.di;

import android.content.Context;
import android.view.View;

import com.example.domain.interactors.OverviewRepository;
import com.example.nazmuddinmavliwala.ewallet.R;
import com.example.nazmuddinmavliwala.ewallet.base.views.ViewDelegate;
import com.example.nazmuddinmavliwala.ewallet.base.views.ViewManager;
import com.example.nazmuddinmavliwala.ewallet.app.di.identifiers.ScopedActivity;
import com.example.nazmuddinmavliwala.ewallet.ui.overview.models.TransactionsVO;
import com.example.nazmuddinmavliwala.ewallet.ui.overview.presenters.OverviewView;
import com.example.nazmuddinmavliwala.ewallet.ui.overview.views.fragments.OverviewFragment;
import com.example.nazmuddinmavliwala.ewallet.ui.overview.views.viewdelegates.ErrorViewDelegate;
import com.example.nazmuddinmavliwala.ewallet.ui.overview.views.viewdelegates.LoaderViewDelegate;
import com.example.nazmuddinmavliwala.ewallet.ui.overview.views.viewdelegates.OverviewViewDelegate;
import com.nazmuddinmavliwala.ewallet.repository.OverviewDataRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nazmuddinmavliwala on 20/10/2017.
 */


@Module
@ScopedActivity
public class OverviewModule {

    private OverviewFragment fragment;

    public OverviewModule(OverviewFragment fragment) {
        this.fragment = fragment;
    }

    @ScopedActivity
    @Provides
    public OverviewView provideView() {
        return this.fragment;
    }

    @ScopedActivity
    @Provides
    public OverviewRepository provideRepo(OverviewDataRepository repository) {
        return repository;
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
    public ViewManager<TransactionsVO> provideViewManager(ErrorViewDelegate errorViewDelegate,
                                                          LoaderViewDelegate loaderViewDelegate,
                                                          OverviewViewDelegate overviewViewDelegate) {

        List<ViewDelegate<TransactionsVO>> viewDelegates = new ArrayList<>();
        viewDelegates.add(errorViewDelegate);
        viewDelegates.add(loaderViewDelegate);
        viewDelegates.add(overviewViewDelegate);
        return new ViewManager<>(viewDelegates);
    }

}
