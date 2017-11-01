package com.example.nazmuddinmavliwala.ewallet.ui.reports.di;

import com.example.nazmuddinmavliwala.ewallet.ui.reports.views.ReportsFragment;

import dagger.Module;

/**
 * Created by nazmuddinmavliwala on 20/10/2017.
 */

@Module
public class ResportsModule {

    private ReportsFragment fragment;

    public ResportsModule(ReportsFragment fragment) {
        this.fragment = fragment;
    }
}
