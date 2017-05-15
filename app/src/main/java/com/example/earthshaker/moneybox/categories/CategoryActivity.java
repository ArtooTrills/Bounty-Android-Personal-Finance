package com.example.earthshaker.moneybox.categories;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.earthshaker.moneybox.R;
import com.example.earthshaker.moneybox.budget.BudgetConfig;
import com.example.earthshaker.moneybox.budget.EnterAmountDialog;
import com.example.earthshaker.moneybox.budget.eventbus.BudgteEventBus;
import com.example.earthshaker.moneybox.common.BaseActivity;

import de.greenrobot.event.EventBus;

/**
 * Created by earthshaker on 14/5/17.
 */

public class CategoryActivity extends BaseActivity {
    private String source;
    private SelectCategoryViewHolder selectCategoryViewHolder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUI(R.layout.activity_category, R.id.parent_coordinatior_layout);
        EventBus.getDefault().register(this, getResources().getInteger(R.integer.level_1));
    }

    @Override
    protected void setupViewHolder(View view) {
        selectCategoryViewHolder = new SelectCategoryViewHolder(this, view);

    }

    @Override
    public void getIntents() {
        source = getIntent().getStringExtra("source");
    }

    public void onEventMainThread(CategoryEvent.CategorySelected event) {
        if (source != null && source.equalsIgnoreCase("budget")) {
            BudgetConfig budgetConfig = new BudgetConfig();
            budgetConfig.setCategory(event.getCategory());

            EnterAmountDialog enterAmountDialog = EnterAmountDialog.newInstance(this, budgetConfig);
            enterAmountDialog.show(getFragmentManager(), "ENTER_AMOUNT_DIALOG");
            getSupportFragmentManager().executePendingTransactions();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        selectCategoryViewHolder.unRegisterEventBus();
    }

    public void onEventMainThread(CategoryEvent.FinishACtivity event) {
        finish();
    }
}
