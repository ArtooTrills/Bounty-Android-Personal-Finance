package com.example.nazmuddinmavliwala.ewallet.ui.addexpensemoney.di;

import com.example.nazmuddinmavliwala.ewallet.app.di.identifiers.ChildActivity;
import com.example.nazmuddinmavliwala.ewallet.ui.addexpensemoney.views.AddExpenseMoneyActivity;

import dagger.Subcomponent;

/**
 * Created by nazmuddinmavliwala on 20/10/2017.
 */

@ChildActivity
@Subcomponent(modules = AddExpenseMoneyModule.class)
public interface AddExpenseMoneyComponent {
    void inject(AddExpenseMoneyActivity activity);
}
