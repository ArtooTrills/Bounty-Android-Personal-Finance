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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUI(R.layout.activity_transaction_list, R.id.parent_transaction_list_activity);
    }

    @Override
    protected void setupViewHolder(View view) {
        initializeChildActivityToolbar("Transaction List");
        onBackArrowUp();
       new TransactionLlistViewHolder(this, view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
