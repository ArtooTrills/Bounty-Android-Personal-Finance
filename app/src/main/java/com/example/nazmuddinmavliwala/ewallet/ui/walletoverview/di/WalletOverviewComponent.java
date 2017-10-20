package com.example.nazmuddinmavliwala.ewallet.ui.walletoverview.di;

import com.example.nazmuddinmavliwala.ewallet.di.identifiers.ChildActivity;

import dagger.Subcomponent;

/**
 * Created by nazmuddinmavliwala on 20/10/2017.
 */

@ChildActivity
@Subcomponent(modules = WalletOverviewModule.class)
public interface WalletOverviewComponent {

}
