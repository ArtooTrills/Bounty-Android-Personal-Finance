package com.chandilsachin.personal_finance.dagger;


import com.chandilsachin.personal_finance.database.LocalRepo;
import com.chandilsachin.personal_finance.modules.addExpense.AddExpenseViewModel;
import com.chandilsachin.personal_finance.modules.expenseList.ExpenseListViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppContext.class, DatabaseModule.class})
public interface DatabaseComponent {

    void inject(LocalRepo localRepo);
    void inject(AddExpenseViewModel addExpenseViewModel);
    void inject(ExpenseListViewModel expenseListViewModel);
}
