package com.example.nazmuddinmavliwala.ewallet.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by nazmuddinmavliwala on 14/10/2017.
 */

public class DashboardViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;

    public DashboardViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
