package com.example.nazmuddinmavliwala.ewallet.ui.expenseoverview.di;

import com.example.nazmuddinmavliwala.ewallet.app.di.identifiers.ChildActivity;

import dagger.Subcomponent;

/**
 * Created by nazmuddinmavliwala on 20/10/2017.
 */

@ChildActivity
@Subcomponent(modules = ExpenseOverviewModule.class)
public interface ExpenseOverviewComponent {
}
