package com.example.earthshaker.moneybox.common;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.earthshaker.moneybox.dashboard.activity.DashboardActivity;
import com.example.earthshaker.moneybox.R;

/**
 * Created by earthshaker on 13/5/17.
 */

public class MenuUtils {

    MenuNavigator menuNavigator;

    Context context;

    MenuUtils() {

    }


    public boolean selectMenuItem(MenuItem menuItem, BaseActivity activity, boolean isTaskRoot) {
        //Check to see which item was being clicked and perform appropriate action
        switch (menuItem.getItemId()) {

            case R.id.dashboard:
                if (!(activity instanceof DashboardActivity)) {
                    if (isTaskRoot) {
                        menuNavigator.openDashboard(activity);
                    }
                    activity.finish();
                }
                return true;

            case R.id.accounts:

                if (!(activity instanceof AccountDashboardActivity)) {

                    if (!isTaskRoot) activity.finish();
                    activity.setBackPressCount(0);
                    menuNavigator.openAccountDashboard(activity);
                }
                return true;

            case R.id.expMgr:
                if (!(activity instanceof ExpenseDashboardActivity)) {
                    if (!isTaskRoot) activity.finish();
                    activity.setBackPressCount(0);
                    menuNavigator.openExpenseDashboard(activity);
                }
                return true;

            default:
                Toast.makeText(activity, "Something is Wrong", Toast.LENGTH_SHORT).show();
                return true;
        }
    }

    private void setNavigationDrawerHighlight(Activity activity, NavigationView navigationView) {
        int highlightItem = 0;
        if (activity instanceof DashboardActivity) {
            highlightItem = 0;
        } else if (activity instanceof AnalysisActivity) {
            highlightItem = 1;
        } else if (activity instanceof BudgetActivity) {
            highlightItem = 2;
        }else if(activity instanceof MessagesActivity)
        navigationView.getMenu().getItem(highlightItem).setCheckable(true);
        navigationView.getMenu().getItem(highlightItem).setChecked(true);
    }


}
