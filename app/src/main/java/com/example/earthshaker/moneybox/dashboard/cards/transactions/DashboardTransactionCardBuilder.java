package com.example.earthshaker.moneybox.dashboard.cards.transactions;

import android.app.Activity;
import android.view.View;

import com.example.earthshaker.moneybox.R;
import com.example.earthshaker.moneybox.dashboard.cards.common.BaseCardHolder;

/**
 * Created by earthshaker on 24/5/17.
 */

public class DashboardTransactionCardBuilder extends BaseCardHolder {
    @Override
    public void onDestroy() {

    }

    public static View prepareTransactionCard(Activity context) {
        View view = context.getLayoutInflater().inflate(R.layout.dashboard_transaction_card,null,false);
        new DashTxnCardHolder(context,view);
        return view;
    }
}
