package com.example.earthshaker.moneybox.transaction.activity;

import android.os.Bundle;
import android.view.View;

import com.example.earthshaker.moneybox.R;
import com.example.earthshaker.moneybox.common.BaseActivity;
import com.example.earthshaker.moneybox.transaction.TransactionConfig;

import de.greenrobot.event.EventBus;

/**
 * Created by earthshaker on 13/5/17.
 */

public class TransactionsActivity extends BaseActivity {

    private TransactionActivityViewHolder transactionActivityViewHolder;
    private TransactionConfig transactionConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUI(R.layout.activity_transaction_detail, R.id.parent_coordinator_layout);
        EventBus.getDefault().register(getResources().getInteger(R.integer.level_1));
    }

    @Override
    protected void setupViewHolder(View view) {
        initializeChildActivityToolbar("Transaction");
        onBackArrowUp();
        transactionActivityViewHolder = new TransactionActivityViewHolder(this, view,transactionConfig);
        transactionActivityViewHolder.registerEventBus(R.integer.level_1);
    }

    @Override
    protected void getIntents() {
        super.getIntents();
        transactionConfig = getIntent().getParcelableExtra("transaction");
    }
}
