package com.example.earthshaker.moneybox.budget;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.earthshaker.moneybox.R;
import com.example.earthshaker.moneybox.common.BaseHolderEventBus;
import com.example.earthshaker.moneybox.common.FabUtils;
import com.example.earthshaker.moneybox.common.LayoutNotAddedToXmlException;
import com.example.earthshaker.moneybox.common.NoDataViewHolder;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

/**
 * Created by earthshaker on 14/5/17.
 */

public class BudgetActivityViewHolder extends BaseHolderEventBus {

    // Model Variables
    private CardView totalBudgetCard;
    private RelativeLayout editAmount;
    private LinearLayout budgetAmountLayout;
    private NoDataViewHolder noDataViewHolder;
    private TextView setBudgetText, budgetAmountText;
    private ProgressBar totalBudgetProgressBar;
    private RecyclerView budgetsRecyclerView;
    private FloatingActionMenu fabMenu;
    private FloatingActionButton addCategory, addExpense;

    private Context context;
    BudgetActivityViewHolder(Context context,View view){

        this.context = context;

        //heading to set budget
        editAmount = (RelativeLayout) view.findViewById(R.id.rl_set_budget);
        setBudgetText = (TextView) view.findViewById(R.id.tv_set_budget_text);

        //budget amount spent versus total
        budgetAmountText = (TextView) view.findViewById(R.id.tv_budget_amount);
        budgetAmountLayout = (LinearLayout) view.findViewById(R.id.ll_spent_versus_total);


        addCategory = (FloatingActionButton) view.findViewById(R.id.add_category_fab);
        addExpense = (FloatingActionButton) view.findViewById(R.id.fab_transaction);

        totalBudgetProgressBar = (ProgressBar) view.findViewById(R.id.sb_budget_progress);
        totalBudgetCard = (CardView) view.findViewById(R.id.card_view);

        fabMenu = (FloatingActionMenu) view.findViewById(R.id.fab_menu);
        budgetsRecyclerView = (RecyclerView) view.findViewById(R.id.rv_category_budget_list);

        try {
            noDataViewHolder = new NoDataViewHolder(view, context.getString(R.string.no_budget_set),
                    context.getString(R.string.set_month_and_category_wise_budget),
                    context.getString(R.string.click_to_add_budget));
        } catch (LayoutNotAddedToXmlException e) {
            String TAG = BudgetActivityViewHolder.class.getSimpleName();
            Log.e(TAG, e.toString());
        }

        setupFab();

    }
    private void setupFab() {
        // Setup Fab menu
        fabMenu.setClosedOnTouchOutside(true);
        FabUtils.setRollingFab(fabMenu, budgetsRecyclerView);
    }
    @Override
    protected void refreshData() {

    }

    @Override
    protected void recreateLayout() {

    }


}
