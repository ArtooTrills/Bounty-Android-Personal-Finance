package com.examples.ankit.breakpoint;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.examples.ankit.breakpoint.agreements.SmsAgreementFragment;
import com.examples.ankit.breakpoint.models.SmsReceivedEvent;
import com.examples.ankit.breakpoint.models.Transaction;
import com.examples.ankit.breakpoint.prefences.MyPreferenceManager;
import com.examples.ankit.breakpoint.reports.MonthlyExpenseFragment;
import com.examples.ankit.breakpoint.reports.OverallExpensesFragment;
import com.examples.ankit.breakpoint.reports.TransactionsListFragment;
import com.examples.ankit.breakpoint.sms.SmsLoadingFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements SmsAgreementFragment.OnAgreementInteractionListener,
        SmsLoadingFragment.OnSmsLoadingListener, AddExpenseFragment.OnAddExpenseListener,
        OverallExpensesFragment.OnOverallExpenseClickListener {

    private static final String TAG = "mainActivity";
    @BindView(R.id.content_fragment)
    FrameLayout mFragmentContainer;
    @BindView(R.id.add_expense)
    FloatingActionButton addExpenseButton;
    @BindView(R.id.expenses_scroll_view)
    ScrollView mScrollView;
    private OverallExpensesFragment mOverallExpensesFragment;
    private MonthlyExpenseFragment mMonthlyExpenseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        boolean userConsent = MyPreferenceManager.getUserConsent();
        Fragment fragment;
        if (userConsent) {
            //this is the case where user already given us permission to read sms.
            fragment = new SmsLoadingFragment();
            mOverallExpensesFragment = new OverallExpensesFragment();
            mMonthlyExpenseFragment = new MonthlyExpenseFragment();

        } else {
            // show user consent fragment here.
            fragment = new SmsAgreementFragment();
        }
        loadFragment(fragment);
        hideFab(!userConsent);
        addExpenseButton.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Giving extra padding 1.5 times because we are getting height of + button which has
                // margins and custom view to show circle around it.

                mScrollView.setPadding(10, 10, 10, addExpenseButton.getHeight());

            }
        });
    }

    private void loadFragment(Fragment fragment) {
        loadFragment(fragment, false);
    }

    private void loadFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_fragment, fragment).commitAllowingStateLoss();
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
    }

    private void loadMainScreenFragments(OverallExpensesFragment overallExpensesFragment, MonthlyExpenseFragment monthlyExpenseFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.overall_fragment_container, overallExpensesFragment);
        fragmentTransaction.replace(R.id.monthly_fragment_container, monthlyExpenseFragment);
        fragmentTransaction.commitAllowingStateLoss();

    }

    private void hideFab(boolean hide) {
        if (hide) {
            addExpenseButton.setVisibility(View.GONE);
        } else {
            addExpenseButton.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.add_expense)
    public void onClick(View view) {
        hideFab(true);
        mScrollView.setVisibility(View.GONE);
        AddExpenseFragment fragment = new AddExpenseFragment();
        loadFragment(fragment, true);

    }

    @Override
    public void onAgreementAccepted(boolean accepted) {
        hideFab(!accepted);
        SmsLoadingFragment smsLoadingFragment = new SmsLoadingFragment();
        loadFragment(smsLoadingFragment);
    }


    @Override
    public void onSmsLoaded() {
        showExpensesFragment();
    }

    @Override
    public void onAddExpense(Transaction transaction) {
        FragmentManager manager = getSupportFragmentManager();
        manager.popBackStack();
        hideFab(false);
        if (mOverallExpensesFragment == null) {
            mOverallExpensesFragment = new OverallExpensesFragment();
        } else {
            mOverallExpensesFragment.addOrUpdateChart();
        }

        if (mMonthlyExpenseFragment == null) {
            mMonthlyExpenseFragment = new MonthlyExpenseFragment();
        } else {
            mMonthlyExpenseFragment.addOrUpdateChart();
        }
        mScrollView.setVisibility(View.VISIBLE);
        setTitle(R.string.app_name);
    }

    @Override
    public void onExpenseClick(int type) {
        mScrollView.setVisibility(View.GONE);
        showListFragment(type);
    }

    private void showExpensesFragment() {
        if (mOverallExpensesFragment == null) {
            mOverallExpensesFragment = new OverallExpensesFragment();
        }
        if (mMonthlyExpenseFragment == null) {
            mMonthlyExpenseFragment = new MonthlyExpenseFragment();
        }
        mScrollView.setVisibility(View.VISIBLE);
        loadMainScreenFragments(mOverallExpensesFragment, mMonthlyExpenseFragment);
    }

    private void showListFragment(int transactionType) {
        Fragment fragment = TransactionsListFragment.getInstance(transactionType);
        loadFragment(fragment, true);
        hideFab(true);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        float backStackEntryCount = fm.getBackStackEntryCount();
        if (backStackEntryCount >= 1) {
            //this is workaround to pop Fragments in AppcompatActivity.
            fm.popBackStack();
            hideFab(false);
            refreshScrollVIew();
            mScrollView.setVisibility(View.VISIBLE);
            setTitle(getString(R.string.app_name));
            return;
        }
        super.onBackPressed();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SmsReceivedEvent event) {
        refreshScrollVIew();
    }

    public void refreshScrollVIew() {
        if (mOverallExpensesFragment != null) {
            mOverallExpensesFragment.addOrUpdateChart();
        }

        if (mMonthlyExpenseFragment != null) {
            mMonthlyExpenseFragment.addOrUpdateChart();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
