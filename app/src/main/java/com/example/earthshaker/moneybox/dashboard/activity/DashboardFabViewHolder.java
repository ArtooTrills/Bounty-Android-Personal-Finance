package com.example.earthshaker.moneybox.dashboard.activity;

import android.app.usage.UsageEvents;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.example.earthshaker.moneybox.R;
import com.example.earthshaker.moneybox.dashboard.eventbus.DashboardFabEvent;
import com.example.earthshaker.moneybox.dashboard.eventbus.DashboardFabResponse;
import com.github.clans.fab.FloatingActionMenu;

import de.greenrobot.event.EventBus;

/**
 * Created by earthshaker on 13/5/17.
 */

public class DashboardFabViewHolder {

    private FloatingActionButton fabBudget, fabTransaction;
    private FloatingActionMenu fabMenu;

    DashboardFabViewHolder(View parentView) {
        fabMenu = (FloatingActionMenu) parentView.findViewById(R.id.fab_menu);
        fabTransaction = (FloatingActionButton) parentView.findViewById(R.id.fab_transaction);
        fabBudget = (FloatingActionButton) parentView.findViewById(R.id.fab_budget);
        fabMenu.setClosedOnTouchOutside(true);
        setupListener();

    }

    public boolean isFabOpen() {
        return fabMenu.isOpened();
    }

    public void closeFab() {
        fabMenu.close(true);
    }

    private void setupListener() {

        fabMenu.setOnMenuButtonClickListener(l -> closeFab());

        fabBudget.setOnClickListener(l -> EventBus.getDefault().post(new DashboardFabEvent(DashboardFabResponse.ADD_BUDGET)));

        fabTransaction.setOnClickListener(l -> EventBus.getDefault().post(new DashboardFabEvent(DashboardFabResponse.ADD_TRANSACTION)));

    }
}
