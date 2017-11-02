package com.example.nazmuddinmavliwala.ewallet.ui.overview.di;

import com.example.nazmuddinmavliwala.ewallet.app.di.identifiers.ScopedActivity;
import com.example.nazmuddinmavliwala.ewallet.ui.overview.presenters.TransactionsDomainVoConverter;
import com.example.nazmuddinmavliwala.ewallet.ui.transactions.presenter.TransactionDomainVOConverter;
import com.nazmuddinmavliwala.ewallet.repository.TransactionEntityDOConverter;
import com.nazmuddinmavliwala.ewallet.repository.TransactionEntityDomainConverter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nazmuddinmavliwala on 01/11/2017.
 */

@ScopedActivity
@Module
public class ConverterModule {

    @ScopedActivity
    @Provides
    public TransactionEntityDomainConverter provideEntityDomainConverter() {
        return new TransactionEntityDomainConverter();
    }

    @ScopedActivity
    @Provides
    public TransactionsDomainVoConverter provideTransactionsDomainVOConverter() {
        return new TransactionsDomainVoConverter();
    }

    @ScopedActivity
    @Provides
    public TransactionEntityDOConverter provideTransactionEntityDOConverter() {
        return new TransactionEntityDOConverter();
    }

    @ScopedActivity
    @Provides
    public TransactionDomainVOConverter provideTransactionDomainVOConverter() {
        return new TransactionDomainVOConverter();
    }
}
