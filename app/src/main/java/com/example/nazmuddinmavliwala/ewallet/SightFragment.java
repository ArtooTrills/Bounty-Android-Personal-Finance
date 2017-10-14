package com.example.nazmuddinmavliwala.ewallet;

import android.os.Bundle;
import android.view.View;

/**
 * Fragment for handling view after it has been created and visible to user for the first time.
 *
 * <p>Specially in {@link android.support.v4.view.ViewPager}, the page will be created beforehand
 * but not be visible to user.
 *
 * <p>Call {@link android.support.v4.view.ViewPager#setOffscreenPageLimit(int)} to set the number of
 * pages that should be retained.
 *
 * Reference:
 * <ul>
 * <li><a href="http://stackoverflow.com/questions/10024739/how-to-determine-when-fragment-becomes-visible-in-viewpager">
 * How to determine when Fragment becomes visible in ViewPager</a>
 * </ul>
 */
public abstract class SightFragment extends BaseFragment {

    private boolean mUserSeen = false;
    private boolean mViewCreated = false;

    public SightFragment() {
    }

    /*public boolean isUserSeen() {
        return mUserSeen;
    }

    public boolean isViewCreated() {
        return mViewCreated;
    }*/

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!mUserSeen && isVisibleToUser) {
            mUserSeen = true;
            onUserFirstSight();
            tryViewCreatedFirstSight();
        }
        onUserVisibleChanged(isVisibleToUser);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Override this if you want to get savedInstanceState.
        mViewCreated = true;
        tryViewCreatedFirstSight();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewCreated = false;
        mUserSeen = false;
    }

    private void tryViewCreatedFirstSight() {
        if (mUserSeen && mViewCreated) {
            onViewCreatedFirstSight(getView());
        }
    }

    /**
     * Called when the new created view is visible to user for the first time.
     */
    protected void onViewCreatedFirstSight(View view) {
        // handling here
    }

    /**
     * Called when the fragment's UI is visible to user for the first time.
     *
     * <p>However, the view may not be created currently if in {@link android.support.v4.view.ViewPager}.
     */
    protected void onUserFirstSight() {
    }

    /**
     * Called when the visible state to user has been changed.
     */
    protected void onUserVisibleChanged(boolean visible) {
    }

}