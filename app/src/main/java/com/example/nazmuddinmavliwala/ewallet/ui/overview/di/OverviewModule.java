package com.example.nazmuddinmavliwala.ewallet.ui.overview.di;

import com.example.nazmuddinmavliwala.ewallet.ui.overview.views.OverviewFragment;

import dagger.Module;

/**
 * Created by nazmuddinmavliwala on 20/10/2017.
 */


@Module
public class OverviewModule {

    private OverviewFragment fragment;

    public OverviewModule(OverviewFragment fragment) {
        this.fragment = fragment;
    }

}
