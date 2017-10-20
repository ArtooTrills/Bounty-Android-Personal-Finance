package com.example.nazmuddinmavliwala.ewallet.ui.transactions.views.fragments;

import com.example.nazmuddinmavliwala.ewallet.R;
import com.example.nazmuddinmavliwala.ewallet.SightFragment;
import com.example.nazmuddinmavliwala.ewallet.ui.transactions.models.TransactionsData;
import com.example.nazmuddinmavliwala.ewallet.ui.transactions.presenter.TransactionsView;

import java.util.List;

/**
 * Created by nazmuddinmavliwala on 14/10/2017.
 */

public class TransactionsFragment extends SightFragment implements TransactionsView {

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_transactions;
    }

    @Override
    public void appendTransactions(List<TransactionsData> transactionsData) {

    }
}
