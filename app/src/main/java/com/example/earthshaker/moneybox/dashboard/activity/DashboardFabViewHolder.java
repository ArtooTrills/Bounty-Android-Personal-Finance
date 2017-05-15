package com.example.earthshaker.moneybox.dashboard.activity;

import android.view.View;

import com.example.earthshaker.moneybox.R;
import com.example.earthshaker.moneybox.common.eventbus.CommonEvents;

import de.greenrobot.event.EventBus;

/**
 * Created by earthshaker on 13/5/17.
 */

public class DashboardFabViewHolder {

    private com.github.clans.fab.FloatingActionButton fabBudget, fabTransaction;
    private com.github.clans.fab.FloatingActionMenu fabMenu;

    DashboardFabViewHolder(View parentView) {
        fabMenu = (com.github.clans.fab.FloatingActionMenu) parentView.findViewById(R.id.fab_menu);
        fabTransaction = (com.github.clans.fab.FloatingActionButton) parentView.findViewById(R.id.fab_transaction);
        fabBudget = (com.github.clans.fab.FloatingActionButton) parentView.findViewById(R.id.fab_budget);
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

        fabBudget.setOnClickListener(l -> {
            EventBus.getDefault().post(new CommonEvents.StartAddingBudget());
            closeFab();
        });

        fabTransaction.setOnClickListener(l -> {
            closeFab();
            EventBus.getDefault().post(new CommonEvents.StartAddingTransaction());
        });

    }
}
