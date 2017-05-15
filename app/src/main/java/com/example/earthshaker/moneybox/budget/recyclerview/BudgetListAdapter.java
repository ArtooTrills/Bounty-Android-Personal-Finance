package com.example.earthshaker.moneybox.budget.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.earthshaker.moneybox.R;
import com.example.earthshaker.moneybox.budget.BudgetConfig;
import com.example.earthshaker.moneybox.budget.eventbus.BudgteEventBus;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by earthshaker on 14/5/17.
 */

    public class BudgetListAdapter extends RecyclerView.Adapter<BudgetListCardViewHolder> {

    private List<BudgetConfig> mCategoryList;
    private Context context;
    public BudgetListAdapter(Context context, List<BudgetConfig> categoryList) {
        this.context = context;
        mCategoryList = categoryList;
    }

    @Override public BudgetListCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_budget_categorywise, parent, false);
        return new BudgetListCardViewHolder(context,view);
    }

    @Override public void onBindViewHolder(final BudgetListCardViewHolder holder, int position) {
        BudgetConfig budgetConfig = mCategoryList.get(position);
        holder.setBudgetCardForRecyclerView(budgetConfig, this::categoryBudgetClick);
    }

    @Override public int getItemCount() {
        return mCategoryList.size();
    }

    /**
     * @param newBudgetList New budget list to reset data
     */
    public void setData(List<BudgetConfig> newBudgetList) {
        mCategoryList = newBudgetList;
        notifyDataSetChanged();
    }

    /**
     * Open the Budget Detail screen
     */
    private void categoryBudgetClick(BudgetConfig budgetConfig) {
        EventBus.getDefault().post(new BudgteEventBus.OpenBudgetDialog(budgetConfig));
    }
}
