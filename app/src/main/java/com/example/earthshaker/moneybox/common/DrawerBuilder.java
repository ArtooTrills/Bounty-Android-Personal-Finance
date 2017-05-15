package com.example.earthshaker.moneybox.common;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.earthshaker.moneybox.budget.BudgetActivity;
import com.example.earthshaker.moneybox.dashboard.activity.DashboardActivity;
import com.example.earthshaker.moneybox.R;
import com.example.earthshaker.moneybox.transaction.activity.TransactionLIstActivity;


/**
 * Created by earthshaker on 13/5/17.
 */

public class DrawerBuilder {

    public DrawerBuilder() {
    }

    /**
     * @param activity       Activity
     * @param drawerLayout   Drawer Layout
     * @param navigationView Navifation View
     */
    public static void build(final BaseActivity activity, final DrawerLayout drawerLayout,
                      NavigationView navigationView) {
        LinearLayout headerView = (LinearLayout) (LayoutInflater.from(activity)
                .inflate(R.layout.nav_drawer_header, null, false));
        navigationView.addHeaderView(headerView);

        LinearLayout drawerLayoutShare;
        drawerLayoutShare = (LinearLayout) activity.findViewById(R.id.drawer_linear_layout_share);

        LinearLayout headerLayout =
                (LinearLayout) headerView.findViewById(R.id.navigation_drawer_layout);
        LinearLayout freezbarLayout =
                (LinearLayout) activity.findViewById(R.id.navigation_freezbar_layout);


        setNavigationHeaderColor(activity, headerLayout, freezbarLayout);

        drawerLayoutShare.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, activity.getString(R.string.share_message));
            sendIntent.setType("text/plain");
            activity.startActivity(sendIntent);
            drawerLayout.closeDrawers();
        });

    }

    private static void setNavigationHeaderColor(BaseActivity activity, LinearLayout headerLayout,
                                                 LinearLayout freezbarLayout) {
        //this.headerLayout = headerLayout;
        if (activity instanceof DashboardActivity) {
            headerLayout.setBackgroundColor(ContextCompat.getColor(activity, R.color.dashboard_primary));
            freezbarLayout.setBackgroundColor(
                    ContextCompat.getColor(activity, R.color.dashboard_primary_dark));
        }
    }
}
