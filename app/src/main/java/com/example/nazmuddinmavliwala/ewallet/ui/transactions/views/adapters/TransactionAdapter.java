package com.example.nazmuddinmavliwala.ewallet.ui.transactions.views.adapters;

import android.content.Context;

import com.example.nazmuddinmavliwala.ewallet.AdapterDelegate;
import com.example.nazmuddinmavliwala.ewallet.ui.transactions.views.adapterdelegates.CreditDelegate;
import com.example.nazmuddinmavliwala.ewallet.ui.transactions.views.adapterdelegates.DebitDelegate;
import com.example.nazmuddinmavliwala.ewallet.ReactiveRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nazmuddinmavliwala on 20/10/2017.
 */

public class TransactionAdapter extends ReactiveRecyclerAdapter {

    private static final int CREDIT = 1;
    private static final int DEBIT = 2;

    public TransactionAdapter(Context context) {
        super(context);
    }

    @Override
    protected List<AdapterDelegate> initAdapterDelegates() {
        List<AdapterDelegate> adapterDelegates = new ArrayList<>();
        adapterDelegates.add(new CreditDelegate(CREDIT,this.context));
        adapterDelegates.add(new DebitDelegate(DEBIT,this.context));
        return adapterDelegates;
    }
}
