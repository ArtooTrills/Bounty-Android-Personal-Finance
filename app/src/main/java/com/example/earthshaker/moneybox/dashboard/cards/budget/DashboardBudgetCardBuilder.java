package com.example.earthshaker.moneybox.dashboard.cards.budget;

import android.app.Activity;
import android.view.View;

import com.example.earthshaker.moneybox.dashboard.cards.common.BaseCardHolder;

/**
 * Created by earthshaker on 14/5/17.
 */

public class DashboardBudgetCardBuilder extends BaseCardHolder{
    @Override
    public void onDestroy() {

    }

    public static View prepareBudgetCard(Activity context){
        context.getLayoutInflater().inflate(R.id.dasboard_budget_card,null,false);

    }
}
