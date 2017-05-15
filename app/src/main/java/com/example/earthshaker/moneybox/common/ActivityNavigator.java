package com.example.earthshaker.moneybox.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.earthshaker.moneybox.analysis.AnalysisActivity;
import com.example.earthshaker.moneybox.budget.BudgetActivity;
import com.example.earthshaker.moneybox.categories.CategoryActivity;
import com.example.earthshaker.moneybox.dashboard.activity.DashboardActivity;
import com.example.earthshaker.moneybox.transaction.TransactionConfig;
import com.example.earthshaker.moneybox.transaction.activity.TransactionLIstActivity;
import com.example.earthshaker.moneybox.transaction.activity.TransactionsActivity;

/**
 * Created by earthshaker on 13/5/17.
 */

public class ActivityNavigator {

    public static void startActivity(Activity context, Class targetActivity) {
        Intent intent = new Intent(context, targetActivity);
        context.startActivity(intent);
    }


    public static void openCategoryActivity(BaseActivity context, String category) {

        Intent intent = new Intent(context, CategoryActivity.class);
        intent.putExtra("source", category);
        context.startActivity(intent);
    }

    public static void openTransactionActivity(Activity context, TransactionConfig transactionConfig) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("transaction", transactionConfig);
        Intent intent = new Intent(context, TransactionsActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);


    }

    public static void openBudgetActivity(BaseActivity dashboardActivity) {
        startActivity(dashboardActivity, BudgetActivity.class);
    }

    public static void openAnalysisActivity(BaseActivity dashboardActivity) {
        startActivity(dashboardActivity, AnalysisActivity.class);
    }

    public static void openDashboard(BaseActivity activity) {
        startActivity(activity, DashboardActivity.class);
    }

    public static void openTransactionLIstActivity(BaseActivity activity) {
        startActivity(activity, TransactionLIstActivity.class);
    }


}