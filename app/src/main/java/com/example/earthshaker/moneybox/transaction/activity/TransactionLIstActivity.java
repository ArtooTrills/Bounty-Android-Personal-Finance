package com.example.earthshaker.moneybox.transaction.activity;

import android.os.Bundle;
import android.view.View;

import com.example.earthshaker.moneybox.R;
import com.example.earthshaker.moneybox.common.ActivityNavigator;
import com.example.earthshaker.moneybox.common.BaseActivity;
import com.example.earthshaker.moneybox.transaction.eventbus.TransactionsEventBus;

import de.greenrobot.event.EventBus;

/**
 * Created by earthshaker on 15/5/17.
 */

public class TransactionLIstActivity extends BaseActivity {

    private TransactionLlistViewHolder transactionLlistViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUI(R.layout.activity_transaction_list, R.id.parent_transaction_list_activity);
        EventBus.getDefault().register(this, getResources().getInteger(R.integer.level_1));
    }

    @Override
    protected void setupViewHolder(View view) {
        initializeChildActivityToolbar("Transaction List");
        onBackArrowUp();
        transactionLlistViewHolder = new TransactionLlistViewHolder(this, view);
/*
        transactionLlistViewHolder.registerEventBus(getResources().getInteger(R.integer.level_1));
*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        transactionLlistViewHolder.unRegisterEventBus();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(TransactionsEventBus.OpenTransaction event){
        ActivityNavigator.openTransactionActivity(this,event.getTransactionConfig());
    }
}
