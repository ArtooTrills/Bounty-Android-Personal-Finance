package com.example.earthshaker.moneybox.common;

import com.example.earthshaker.moneybox.dashboard.activity.DashboardActivity;

/**
 * Created by earthshaker on 13/5/17.
 */

public class BaseActivityNavigators extends ActivityNavigator {

    public static void openDashboard(BaseActivity baseActivity) {
        startActivity(baseActivity, DashboardActivity.class);
    }
}
