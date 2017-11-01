package com.example.nazmuddinmavliwala.ewallet.ui.transactions.di;

import android.os.Parcel;

import com.example.nazmuddinmavliwala.ewallet.app.di.components.ApplicationComponent;
import com.example.nazmuddinmavliwala.ewallet.app.di.identifiers.ScopedActivity;
import com.example.nazmuddinmavliwala.ewallet.app.di.modules.RxModule;
import com.example.nazmuddinmavliwala.ewallet.ui.overview.di.ConverterModule;
import com.example.nazmuddinmavliwala.ewallet.ui.transactions.views.fragments.TransactionsFragment;

import dagger.Component;

/**
 * Created by nazmuddinmavliwala on 01/11/2017.
 */


@ScopedActivity
@Component(
        dependencies = ApplicationComponent.class,
        modules = {
                RxModule.class,
                ConverterModule.class,
                TransactionsModule.class
        }
)
public interface TransactionComponent {
    void inject(TransactionsFragment fragment);
}
