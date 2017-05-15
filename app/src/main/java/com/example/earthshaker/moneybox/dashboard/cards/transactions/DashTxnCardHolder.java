package com.example.earthshaker.moneybox.dashboard.cards.transactions;

import android.app.Activity;
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
import com.example.earthshaker.moneybox.transaction.activity.TransactionListAdapter;
import com.example.earthshaker.moneybox.transaction.dao.TransactionInfoDAo;

import java.util.List;

/**
 * Created by earthshaker on 15/5/17.
 */

public class DashTxnCardHolder extends BaseHolderEventBus {

    private Activity context;
    private RecyclerView recyclerView;
    private List<TransactionConfig> transactionConfigs;
    private NoDataViewHolder noDataViewHolder;

    private TransactionListAdapter transactionListAdapter;

    public DashTxnCardHolder(Activity context, View view) {
        this.context = context;
        recyclerView = (RecyclerView) view.findViewById(R.id.ll_txn_card);
        try {
            noDataViewHolder = new NoDataViewHolder(view, context.getString(R.string.no_budget_set),
                    context.getString(R.string.set_month_and_category_wise_budget),
                    context.getString(R.string.click_to_add_budget));
        } catch (LayoutNotAddedToXmlException e) {
            String TAG = BudgetActivityViewHolder.class.getSimpleName();
            Log.e(TAG, e.toString());
        }

        transactionConfigs = TransactionInfoDAo.fetchTopTwoTxns();
        setData();
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
