package com.example.nazmuddinmavliwala.ewallet.ui.transactions.views.adapters;

import android.content.Context;

import com.example.nazmuddinmavliwala.ewallet.base.views.AdapterDelegate;
import com.example.nazmuddinmavliwala.ewallet.base.views.ReactiveRecyclerAdapter;
import com.example.nazmuddinmavliwala.ewallet.ui.transactions.views.adapterdelegates.TransactionDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nazmuddinmavliwala on 20/10/2017.
 */

public class TransactionAdapter extends ReactiveRecyclerAdapter {

    private static final int TRANSACTION = 1;

    public TransactionAdapter(Context context) {
        super(context);
    }

    @Override
    protected List<AdapterDelegate> initAdapterDelegates() {
        List<AdapterDelegate> adapterDelegates = new ArrayList<>();
        adapterDelegates.add(new TransactionDelegate(TRANSACTION,this.context));
        return adapterDelegates;
    }
}
