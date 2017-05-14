package com.example.earthshaker.moneybox.dashboard.eventbus;

/**
 * Created by earthshaker on 24/5/17.
 */
public class DashboardFabEvent {
    private DashboardFabResponse dashboardFabResponse;

    public DashboardFabEvent(DashboardFabResponse dashboardFabResponse) {
        this.dashboardFabResponse = dashboardFabResponse;
    }

    public DashboardFabResponse getDashboardFabResponse() {
        return dashboardFabResponse;
    }
}
