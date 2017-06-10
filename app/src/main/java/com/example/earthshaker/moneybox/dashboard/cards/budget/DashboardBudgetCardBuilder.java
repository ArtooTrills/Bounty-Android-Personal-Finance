package com.example.earthshaker.moneybox.dashboard.cards.budget;

import android.app.Activity;
import android.view.View;

import com.example.earthshaker.moneybox.R;
import com.example.earthshaker.moneybox.dashboard.cards.common.BaseCardHolder;

/**
 * Created by earthshaker on 14/5/17.
 */

public class DashboardBudgetCardBuilder extends BaseCardHolder{
    @Override
    public void onDestroy() {

    }

    public static View prepareBudgetCard(Activity context){
        View view = context.getLayoutInflater().inflate(R.layout.dashboard_budget_card,null,false);
        new DashboardBudgetCardViewHolder(context,view);
        return view;

    }
}
