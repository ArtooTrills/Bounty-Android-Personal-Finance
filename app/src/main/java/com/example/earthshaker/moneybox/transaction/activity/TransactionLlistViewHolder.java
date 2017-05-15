package com.example.earthshaker.moneybox.transaction.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.earthshaker.moneybox.R;
import com.example.earthshaker.moneybox.budget.BudgetActivityViewHolder;
import com.example.earthshaker.moneybox.common.BaseHolderEventBus;
import com.example.earthshaker.moneybox.common.LayoutNotAddedToXmlException;
import com.example.earthshaker.moneybox.common.NoDataViewHolder;
import com.example.earthshaker.moneybox.transaction.TransactionConfig;
import com.example.earthshaker.moneybox.transaction.dao.TransactionInfoDAo;

import java.util.List;

/**
 * Created by earthshaker on 15/5/17.
 */

public class TransactionLlistViewHolder extends BaseHolderEventBus {

    private RecyclerView recyclerView;
    private Context context;
    private TransactionListAdapter transactionListAdapter;
    private List<TransactionConfig> mTransactionList;
    private NoDataViewHolder noDataViewHolder;

    public TransactionLlistViewHolder(Context context, View view) {
        this.context = context;
        recyclerView = (RecyclerView) view.findViewById(R.id.transaction_rv);
        try {
            noDataViewHolder = new NoDataViewHolder(view, "No Transaction Available", "Make transactions to view this page",
                    "Click +button on previous page");
        } catch (LayoutNotAddedToXmlException e) {
            String TAG = BudgetActivityViewHolder.class.getSimpleName();
            Log.e(TAG, e.toString());
        }
        fetchData();
        setupAdapter();
    }

    private void fetchData() {
        mTransactionList = TransactionInfoDAo.getTransactionList();
    }

    private void setupAdapter() {
        if (mTransactionList.size() > 0) {
            if(transactionListAdapter == null){
                transactionListAdapter = new TransactionListAdapter(context, mTransactionList);
                recyclerView.setAdapter(transactionListAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }
            else{
                transactionListAdapter.setData(mTransactionList);
            }
        } else
            noDataViewHolder.showNoDataLayout();
    }

    @Override
    protected void refreshData() {
        fetchData();
        transactionListAdapter.setData(mTransactionList);
    }

    @Override
    protected void recreateLayout() {

    }
}
