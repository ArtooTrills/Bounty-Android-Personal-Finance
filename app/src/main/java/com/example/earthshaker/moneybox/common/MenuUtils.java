package com.example.earthshaker.moneybox.common;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.earthshaker.moneybox.analysis.AnalysisActivity;
import com.example.earthshaker.moneybox.budget.BudgetActivity;
import com.example.earthshaker.moneybox.dashboard.activity.DashboardActivity;
import com.example.earthshaker.moneybox.R;
import com.example.earthshaker.moneybox.transaction.activity.TransactionLIstActivity;

/**
 * Created by earthshaker on 13/5/17.
 */

public class MenuUtils {


    Context context;

    MenuUtils() {

    }


    public static boolean selectMenuItem(MenuItem menuItem, BaseActivity activity, boolean isTaskRoot) {
        //Check to see which item was being clicked and perform appropriate action
        switch (menuItem.getItemId()) {

            case R.id.dashboard:
                if (!(activity instanceof DashboardActivity)) {
                    if (isTaskRoot) {
                        ActivityNavigator.openDashboard(activity);
                    }
                    activity.finish();
                }
                return true;

            case R.id.analysis:

                if (!(activity instanceof AnalysisActivity)) {

                    if (!isTaskRoot) activity.finish();
                    activity.setBackPressCount(0);
                    ActivityNavigator.openAnalysisActivity(activity);
                }
                return true;
            case R.id.transaction:

                if (!(activity instanceof TransactionLIstActivity)) {

                    if (!isTaskRoot) activity.finish();
                    activity.setBackPressCount(0);
                    ActivityNavigator.openTransactionLIstActivity(activity);
                }
                return true;

            case R.id.budget:
                if (!(activity instanceof BudgetActivity)) {
                    if (!isTaskRoot) activity.finish();
                    activity.setBackPressCount(0);
                    ActivityNavigator.openBudgetActivity(activity);
                }
                return true;

            default:
                Toast.makeText(activity, "Something is Wrong", Toast.LENGTH_SHORT).show();
                return true;
        }
    }
}
