package com.example.nazmuddinmavliwala.ewallet.ui.transactions.presenter;

import com.example.nazmuddinmavliwala.ewallet.base.presenters.BaseView;
import com.example.nazmuddinmavliwala.ewallet.ui.transactions.models.TransactionsData;

import java.util.List;

/**
 * Created by nazmuddinmavliwala on 20/10/2017.
 */

public interface TransactionsView extends BaseView {
    void appendTransactions(List<TransactionsData> transactionsData);
}
