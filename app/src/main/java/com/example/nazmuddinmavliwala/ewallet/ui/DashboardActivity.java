package com.example.nazmuddinmavliwala.ewallet.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.nazmuddinmavliwala.ewallet.R;
import com.example.nazmuddinmavliwala.ewallet.base.views.BaseActivity;
import com.example.nazmuddinmavliwala.ewallet.ui.overview.views.fragments.OverviewFragment;
import com.example.nazmuddinmavliwala.ewallet.ui.transactions.views.fragments.TransactionsFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by nazmuddinmavliwala on 14/10/2017.
 */

public class DashboardActivity extends BaseActivity implements OnTabSelectListener {

    @BindView(R.id.vp_dashboard)
    ViewPager vpDashboard;

    @BindView(R.id.bottom_bar)
    BottomBar bottomBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new OverviewFragment());
        fragments.add(new TransactionsFragment());
        DashboardViewPagerAdapter adapter = new DashboardViewPagerAdapter(getSupportFragmentManager(),fragments);
        vpDashboard.setAdapter(adapter);
        bottomBar.setOnTabSelectListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_dashboard;
    }

    @Override
    public void onTabSelected(int tabId) {
        switch (tabId) {
            case R.id.tab_overview:
                vpDashboard.setCurrentItem(0);
                break;
            case R.id.tab_transactions:
                vpDashboard.setCurrentItem(1);
                break;
        }
    }
}
