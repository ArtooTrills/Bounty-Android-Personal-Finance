package com.example.earthshaker.moneybox.budget.recyclerview;

import android.content.Context;
import android.os.Build;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.earthshaker.moneybox.R;
import com.example.earthshaker.moneybox.budget.BudgetConfig;
import com.example.earthshaker.moneybox.budget.BugdetHelper;
import com.example.earthshaker.moneybox.common.CategoryUtils;
import com.example.earthshaker.moneybox.common.callback.ParameterCallback;
import com.squareup.picasso.Picasso;

/**
 * Created by earthshaker on 14/5/17.
 */

public class BudgetListCardViewHolder extends RecyclerView.ViewHolder {


    Context context;

    private TextView categoryName, amount;
    private ImageView categoryImage;
    private LinearLayout outerLinearLayout;

    public BudgetListCardViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        categoryName = (TextView) itemView.findViewById(R.id.tv_category_name);
        categoryImage = (ImageView) itemView.findViewById(R.id.category_image);
        amount = (TextView) itemView.findViewById(R.id.tv_budget_amount);
        outerLinearLayout = (LinearLayout) itemView.findViewById(R.id.outer_ll);
    }

    /**
     * Budget Card for recycler View
     *
     * @param budgetConfig Budget Config
     * @param callback     Budget Config on Selection
     */
    public void setBudgetCardForRecyclerView(BudgetConfig budgetConfig,
                                             ParameterCallback<BudgetConfig> callback) {
        setBasicBudgetCard(budgetConfig, callback);
    }

    private void setBasicBudgetCard(BudgetConfig budgetConfig,
                                    ParameterCallback<BudgetConfig> callback) {
        if ("ALL".equals(budgetConfig.getCategory())) {
            categoryName.setText("Total Budget");
        } else {
            categoryName.setText((budgetConfig.getCategory()));
        }
        Picasso.with(context)
                .load(CategoryUtils.getCategoryIcon(budgetConfig.getCategory()))
                .placeholder(R.drawable.background_fill)
                .into(categoryImage);

        amount.setText(BugdetHelper.getBudgetAmountString(budgetConfig.getSpent(),
                budgetConfig.getTotalamount()));
        attachListener(budgetConfig, callback);
    }

    private void attachListener(BudgetConfig budgetConfig, ParameterCallback<BudgetConfig> callback) {
        outerLinearLayout.setOnClickListener(view -> {
            callback.onResponse(budgetConfig);
        });
    }
}

