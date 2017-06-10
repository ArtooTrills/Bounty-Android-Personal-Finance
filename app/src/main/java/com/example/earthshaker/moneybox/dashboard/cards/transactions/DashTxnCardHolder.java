package com.example.earthshaker.moneybox.dashboard.cards.transactions;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.earthshaker.moneybox.R;
import com.example.earthshaker.moneybox.budget.BudgetActivityViewHolder;
import com.example.earthshaker.moneybox.common.BaseHolderEventBus;
import com.example.earthshaker.moneybox.common.LayoutNotAddedToXmlException;
import com.example.earthshaker.moneybox.common.NoDataViewHolder;
import com.example.earthshaker.moneybox.transaction.TransactionConfig;
import com.example.earthshaker.moneybox.transaction.activity.TransactionListAdapter;
import com.example.earthshaker.moneybox.transaction.dao.TransactionInfoDAo;
import com.example.earthshaker.moneybox.transaction.eventbus.TransactionsEventBus;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by earthshaker on 15/5/17.
 */

public class DashTxnCardHolder extends BaseHolderEventBus {

    private Activity context;
    private RecyclerView recyclerView;
    private List<TransactionConfig> transactionConfigs;
    private NoDataViewHolder noDataViewHolder;
    private TextView viewAll;
    private TransactionListAdapter transactionListAdapter;

    public DashTxnCardHolder(Activity context, View view) {
        this.context = context;
        recyclerView = (RecyclerView) view.findViewById(R.id.ll_txn_card);
        viewAll = (TextView) view.findViewById(R.id.view_all_accounts);
        try {
            noDataViewHolder = new NoDataViewHolder(view, "No transactions Available", "You can see your transactions here",
                    " Click + button to add transaction");
        } catch (LayoutNotAddedToXmlException e) {
            String TAG = BudgetActivityViewHolder.class.getSimpleName();
            Log.e(TAG, e.toString());
        }
        transactionConfigs = TransactionInfoDAo.fetchTopTwoTxns();
        setData();
        setListeners();
    }

    private void setListeners() {
        viewAll.setOnClickListener(l -> EventBus.getDefault().post(new TransactionsEventBus.OpenAllTxns()));
    }

    private void setData() {
        if (transactionConfigs != null && !transactionConfigs.isEmpty()) {
            noDataViewHolder.hideNoDataLayout();
            recyclerView.setVisibility(View.VISIBLE);
            if (transactionListAdapter == null) {
                transactionListAdapter = new TransactionListAdapter(context, transactionConfigs);
                recyclerView.setAdapter(transactionListAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                transactionListAdapter.setData(transactionConfigs);
            }
        } else {
            noDataViewHolder.showNoDataLayout();
        }

    }


    @Override
    protected void refreshData() {
        transactionConfigs = TransactionInfoDAo.fetchTopTwoTxns();
        setData();
    }

    @Override
    protected void recreateLayout() {

    }
}
