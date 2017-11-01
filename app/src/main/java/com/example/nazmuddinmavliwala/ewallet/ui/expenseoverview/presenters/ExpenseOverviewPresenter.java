package com.example.nazmuddinmavliwala.ewallet.ui.expenseoverview.presenters;

import com.example.nazmuddinmavliwala.ewallet.base.presenters.BasePresenter;

import javax.inject.Inject;

/**
 * Created by nazmuddinmavliwala on 20/10/2017.
 */

public class ExpenseOverviewPresenter extends BasePresenter<ExpenseOverviewView> {

    @Inject
    public ExpenseOverviewPresenter(ExpenseOverviewView view) {
        super(view);
    }
}
