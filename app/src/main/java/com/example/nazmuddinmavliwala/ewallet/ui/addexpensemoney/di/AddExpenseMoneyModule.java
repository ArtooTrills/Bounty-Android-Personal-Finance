package com.example.nazmuddinmavliwala.ewallet.ui.addexpensemoney.di;

import com.example.nazmuddinmavliwala.ewallet.app.di.identifiers.ChildActivity;
import com.example.nazmuddinmavliwala.ewallet.ui.addexpensemoney.views.AddExpenseMoneyActivity;

import dagger.Module;

/**
 * Created by nazmuddinmavliwala on 20/10/2017.
 */

@ChildActivity
@Module
public class AddExpenseMoneyModule {

    private AddExpenseMoneyActivity activity;

    public AddExpenseMoneyModule(AddExpenseMoneyActivity activity) {
        this.activity = activity;
    }
}
