package com.example.earthshaker.moneybox.budget;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.View;

import com.example.earthshaker.moneybox.R;
import com.example.earthshaker.moneybox.budget.eventbus.BudgteEventBus;
import com.example.earthshaker.moneybox.common.ActivityNavigator;
import com.example.earthshaker.moneybox.common.BaseActivity;
import com.example.earthshaker.moneybox.common.eventbus.CommonEvents;

import de.greenrobot.event.EventBus;

/**
 * Created by earthshaker on 14/5/17.
 */

public class BudgetActivity extends BaseActivity {

    private  BudgetActivityViewHolder budgetActivityViewHolder;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUI(R.layout.activity_budget, R.id.parent_coordinator_layout);
        EventBus.getDefault().register(getResources().getInteger(R.integer.level_1));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void setupViewHolder(View view) {
        initializeToolbar("Budget");
        initializeNavigationMenu();
        budgetActivityViewHolder = new BudgetActivityViewHolder(this,view);
        budgetActivityViewHolder.registerEventBus(getResources().getInteger(R.integer.level_1));

    }

    public void onEventMainThread(BudgteEventBus.AddCategoryEvent event){
        ActivityNavigator.openCategoryActivity(this);
    }

    public void onEventMainThread(BudgteEventBus.OpenBudgetDialog event){
        EnterAmountDialog enterAmountDialog = EnterAmountDialog.newInstance(event.getBudgetConfig());
        enterAmountDialog.show(getFragmentManager(), "ENTER_AMOUNT_DIALOG");
        getSupportFragmentManager().executePendingTransactions();
    }

    public void onEventMainThread(CommonEvents.AddBudget event){
        budgetActivityViewHolder.refreshData();
    }

}
