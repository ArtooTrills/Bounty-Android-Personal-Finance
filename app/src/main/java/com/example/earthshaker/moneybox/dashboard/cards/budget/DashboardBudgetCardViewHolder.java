package com.example.earthshaker.moneybox.dashboard.cards.budget;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.earthshaker.moneybox.R;
import com.example.earthshaker.moneybox.budget.BudgetActivityViewHolder;
import com.example.earthshaker.moneybox.budget.BudgetConfig;
import com.example.earthshaker.moneybox.budget.dao.BudgetInfoDao;
import com.example.earthshaker.moneybox.budget.eventbus.BudgteEventBus;
import com.example.earthshaker.moneybox.budget.recyclerview.BudgetListAdapter;
import com.example.earthshaker.moneybox.budget.recyclerview.BudgetListCardViewHolder;
import com.example.earthshaker.moneybox.common.ActivityNavigator;
import com.example.earthshaker.moneybox.common.LayoutNotAddedToXmlException;
import com.example.earthshaker.moneybox.common.NoDataViewHolder;
import com.example.earthshaker.moneybox.common.eventbus.CommonEvents;
import com.example.earthshaker.moneybox.dashboard.BaseCardViewHolder;
import com.example.earthshaker.moneybox.dashboard.cards.common.BaseCardHolder;
import com.example.earthshaker.moneybox.transaction.activity.TransactionListAdapter;
import com.example.earthshaker.moneybox.transaction.dao.TransactionInfoDAo;
import com.example.earthshaker.moneybox.transaction.eventbus.TransactionsEventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by earthshaker on 14/5/17.
 */

public class DashboardBudgetCardViewHolder extends BaseCardViewHolder {

    private Activity context;
    private List<BudgetConfig> budgetConfigs;
    private RecyclerView recyclerView;
    private NoDataViewHolder noDataViewHolder;

    BudgetListAdapter budgetListAdapter;
    private TextView viewAll;

    DashboardBudgetCardViewHolder(Activity context, View view) {
        registerEventBus(context.getResources().getInteger(R.integer.level_2));
        this.context = context;
        recyclerView = (RecyclerView) view.findViewById(R.id.ll_total_budget);
        viewAll = (TextView) view.findViewById(R.id.tv_view_all_budget);
        try {
            noDataViewHolder = new NoDataViewHolder(view, context.getString(R.string.no_budget_set),
                    context.getString(R.string.set_month_and_category_wise_budget),
                    context.getString(R.string.click_to_add_budget));
        } catch (LayoutNotAddedToXmlException e) {
            String TAG = BudgetActivityViewHolder.class.getSimpleName();
            Log.e(TAG, e.toString());
        }

        budgetConfigs = BudgetInfoDao.fetchTopTwoBudgets();
        setUpData();
        setListener();
    }

    private void setListener() {
        viewAll.setOnClickListener(l -> EventBus.getDefault().post(new BudgteEventBus.OpenBudget()));
    }

    private void setUpData() {
        if (budgetConfigs != null && !budgetConfigs.isEmpty()) {
            noDataViewHolder.hideNoDataLayout();
            recyclerView.setVisibility(View.VISIBLE);
            if (budgetListAdapter == null) {
                budgetListAdapter = new BudgetListAdapter(context, budgetConfigs);
                recyclerView.setAdapter(budgetListAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                budgetListAdapter.setData(budgetConfigs);
            }
        } else {
            noDataViewHolder.showNoDataLayout();
        }

    }


    @Override
    public void onDestroy() {

    }

    @Override
    protected void refreshData() {
        budgetConfigs = BudgetInfoDao.fetchTopTwoBudgets();
        setUpData();
    }

    @Override
    protected void recreateLayout() {

    }

    public void onEventMainThread(CommonEvents.BudgetModifiedEvent event) {
        refreshData();
    }
}
