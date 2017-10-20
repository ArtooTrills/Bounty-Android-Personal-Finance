package com.example.nazmuddinmavliwala.ewallet.ui.transactions.presenter;

import com.example.nazmuddinmavliwala.ewallet.base.presenters.Repository;
import com.example.nazmuddinmavliwala.ewallet.data.TransactionEntity;
import com.example.nazmuddinmavliwala.ewallet.domain.model.Transactions;

import java.util.List;

import rx.Observable;


/**
 * Created by nazmuddinmavliwala on 20/10/2017.
 */

public interface TransactionsRepository extends Repository {
    Observable<List<Transactions>> fetchTransactions();
}
