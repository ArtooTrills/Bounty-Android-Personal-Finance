package com.example.earthshaker.moneybox.dashboard.activity;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.example.earthshaker.moneybox.R;
import com.example.earthshaker.moneybox.common.BaseActivity;
import com.example.earthshaker.moneybox.dashboard.cards.transactions.DashboardTransactionCardBuilder;

/**
 * Created by earthshaker on 13/5/17.
 */

public class DashboardViewHolder {

    private Activity mContext;
    private View mParentView;
    private DashboardFabViewHolder dashboardFabViewHolder;
    private LinearLayout cardLayout;

    DashboardViewHolder(BaseActivity context, View view) {
        mContext = context;
        mParentView = view;
        cardLayout = (LinearLayout) mParentView.findViewById(R.id.layout_main);
        createCards();
        createFabMenu();

    }

    private void createCards() {
        removeAllView();
        addCardToView(DashboardTransactionCardBuilder.prepareTransactionCard(mContext));

        createBudgetCard();
        createAnalysisCard();
        createMessageCard();
    }

    private void addCardToView(View view) {
        cardLayout.addView(view);
    }

    private void createTransactionsCard() {
    }

    public void removeAllView() {
        cardLayout.removeAllViews();
    }

    private void createFabMenu() {
        dashboardFabViewHolder = new DashboardFabViewHolder(mParentView);
    }
}
