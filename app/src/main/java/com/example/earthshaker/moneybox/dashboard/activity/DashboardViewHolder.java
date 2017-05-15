package com.example.earthshaker.moneybox.dashboard.activity;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.earthshaker.moneybox.R;
import com.example.earthshaker.moneybox.common.BaseActivity;
import com.example.earthshaker.moneybox.common.BaseHolderEventBus;
import com.example.earthshaker.moneybox.dashboard.cards.budget.DashboardBudgetCardBuilder;
import com.example.earthshaker.moneybox.dashboard.cards.transactions.DashboardTransactionCardBuilder;
import com.example.earthshaker.moneybox.dashboard.eventbus.DashboardEventBus;

import de.greenrobot.event.EventBus;

/**
 * Created by earthshaker on 13/5/17.
 */

public class DashboardViewHolder extends BaseHolderEventBus{

    private Activity mContext;
    private View mParentView;
    private DashboardFabViewHolder dashboardFabViewHolder;
    private LinearLayout linearLayout;
    private CardView syncCard;
    private DashboardFabViewHolder fabBuilder;


    DashboardViewHolder(BaseActivity context, View view) {
        mContext = context;
        mParentView = view;
        linearLayout = (LinearLayout) mParentView.findViewById(R.id.layout_main);
        syncCard = (CardView) mParentView.findViewById(R.id.dashb_sync_card);
        createCards();
        createFabMenu();
        attachListeners();

    }

    private void attachListeners() {
        syncCard.setOnClickListener(l -> {
            EventBus.getDefault().post(new DashboardEventBus.SyncSms());
        });

    }

    private void createCards() {
        removeAllView();
        addCardToView(DashboardTransactionCardBuilder.prepareTransactionCard(mContext));
        addCardToView(DashboardBudgetCardBuilder.prepareBudgetCard(mContext));
    }

    private void addCardToView(View view) {
        linearLayout.addView(view);
    }

    public void removeAllView() {
        linearLayout.removeAllViews();
    }

    private void    createFabMenu() {
        dashboardFabViewHolder = new DashboardFabViewHolder(mParentView);
    }


    @Override
    protected void refreshData() {

    }

    @Override
    protected void recreateLayout() {

    }

    public DashboardFabViewHolder getFabBuilder() {
        return fabBuilder;
    }
}
