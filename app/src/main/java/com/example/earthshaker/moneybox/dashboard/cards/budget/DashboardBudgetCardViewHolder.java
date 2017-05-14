package com.example.earthshaker.moneybox.dashboard.cards.budget;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.earthshaker.moneybox.R;
import com.example.earthshaker.moneybox.dashboard.BaseCardViewHolder;
import com.example.earthshaker.moneybox.dashboard.cards.common.BaseCardHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by earthshaker on 14/5/17.
 */

public class DashboardBudgetCardViewHolder extends BaseCardViewHolder {
    private View parentView;
    private LinearLayout noBudgetLayout;
    private LinearLayout mCardsContainer;
    private TextView budgetMonth;
    private TextView viewAllBudget;

    private List<BudgetListCardViewHolder> budgetHolderList;
    private int position;
    private int previousSize;

    DashboardBudgetCardViewHolder(View view, int position) {
        parentView = view;
        this.position = position;

        mCardsContainer = (LinearLayout) view.findViewById(R.id.ll_total_budget);
        noBudgetLayout = (LinearLayout) view.findViewById(R.id.ll_no_budget_set);

        budgetMonth = (TextView) view.findViewById(R.id.tv_budget_month);
        viewAllBudget = (TextView) view.findViewById(R.id.tv_view_all_budget);
        setUpComponent();
        registerEventBus(context.getResources().getInteger(R.integer.level_0));
        setUpData();
    }

    private void setUpData() {
        dashboardHelper.setRibbonColor(parentView, R.id.card_ribbon_budget, position);
        setCurrentMonthText();
        createIndividualBudgetCard();

        toggleVisibility(mTopThreeBudgets.size() > 0);
        attachListener();
    }

    private void setUpComponent() {
        ComponentFactory.getInstance().removeDashboardBudgetCardComponent();
        ComponentFactory.getInstance().getDashboardBudgetCardComponent().inject(this);
    }

    private void setCurrentMonthText() {
        String currentMonth = DateFormatterConstants.budgetMonthDateFormat.format(new Date());
        budgetMonth.setText(currentMonth);
    }

    private void createIndividualBudgetCard() {
        previousSize = mTopThreeBudgets.size();
        if (budgetHolderList == null) {
            budgetHolderList = new ArrayList<>();
        } else {
            budgetHolderList.clear();
        }

        mCardsContainer.removeAllViews();

        for (BudgetConfig budgetConfig : mTopThreeBudgets) {
            LinearLayout budgetCard =
                    (LinearLayout) layoutInflater.inflate(R.layout.part_item_budget_categorywise, null, false);

            BudgetListCardViewHolder dashBudgetHolder = new BudgetListCardViewHolder(budgetCard);
            dashBudgetHolder.setBudgetCardForNonRecyclerView(budgetConfig, this::onBudgetRowClick);

            budgetHolderList.add(dashBudgetHolder);
            mCardsContainer.addView(budgetCard);
        }
    }

    private void refreshBudgets() {
        for (int i = 0; i < mTopThreeBudgets.size(); i++) {
            budgetHolderList.get(i)
                    .setBudgetCardForNonRecyclerView(mTopThreeBudgets.get(i), this::onBudgetRowClick);
        }
    }

    private void onBudgetRowClick(BudgetConfig budgetConfig) {
        EventBus.getDefault().post(new DashboardBugdetCardEvent(BUDGET_ROW, budgetConfig));
    }

    private void toggleVisibility(boolean showData) {
        if (!showData) {
            mCardsContainer.setVisibility(View.GONE);
            noBudgetLayout.setVisibility(View.VISIBLE);
            viewAllBudget.setText(R.string.budget_go_to_budget);
        } else {
            mCardsContainer.setVisibility(View.VISIBLE);
            noBudgetLayout.setVisibility(View.GONE);
            viewAllBudget.setText(R.string.budget_view_all);
        }
    }

    private void attachListener() {
        parentView.setOnClickListener(v -> {
            dashboardMixpanelHelper.setupAnalytics(context.getString(R.string.dash_all_budget_click));
            EventBus.getDefault().post(new DashboardBugdetCardEvent(BUDGET_CARD, null));
        });
    }

    public void onEventBackgroundThread(AppCommonEvent.BudgetModifiedEvent budgetModifiedEvent) {
        refreshData();
    }

    public void onEventBackgroundThread(
            AppCommonEvent.TransactionModifiedEvent transactionModifiedEvent) {
        refreshData();
    }

    public void onEventMainThread(RefreshBudgetCardLayoutEvent refreshBudgetCardLayoutEvent) {
        recreateLayout();
    }

    @Override public void onDestroy() {
        unRegisterEventBus();
        ComponentFactory.getInstance().removeDashboardBudgetCardComponent();
    }

    @Override protected void refreshData() {
        setUpComponent();
        EventBus.getDefault().post(new RefreshBudgetCardLayoutEvent());
    }

    @Override protected void recreateLayout() {
        toggleVisibility(mTopThreeBudgets.size() > 0);
        if (previousSize != mTopThreeBudgets.size()) {
            createIndividualBudgetCard();
        } else {
            refreshBudgets();
        }
    }

    private class RefreshBudgetCardLayoutEvent {
    }


}
