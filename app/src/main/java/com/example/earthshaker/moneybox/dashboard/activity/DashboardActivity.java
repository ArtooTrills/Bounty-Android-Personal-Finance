package com.example.earthshaker.moneybox.dashboard.activity;


import android.os.Bundle;
import android.view.View;

import com.example.earthshaker.moneybox.R;
import com.example.earthshaker.moneybox.common.BaseActivity;

import de.greenrobot.event.EventBus;

public class DashboardActivity extends BaseActivity {

    DashboardViewHolder dashboardViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUI(R.layout.activity_dashboard, R.id.parent_coordinator_layout);
        EventBus.getDefault().register(this, getResources().getInteger(R.integer.level_0));
    }


    @Override
    protected void setupViewHolder(View view) {
        initializeToolbar("Dashboard");
        initializeNavigationMenu();
        dashboardViewHolder = new DashboardViewHolder(this, view);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
