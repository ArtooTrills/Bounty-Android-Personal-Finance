package com.artoo.finac;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

public class Dashboard extends AppCompatActivity {

    //  DM
    private long mBackPressed;

    private Fragment detailView;
    private Fragment summaryView;
    private Fragment addTxn;

    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    private FloatingActionButton fab;
    private TextView textViewSavings;
    private TextView textViewExpenses;
    private TextView textViewEarnings;

    private float credit;
    private float debit;
    private float savings;

    private final static String TAG = "Finac Dashboard";

    private SharedPreferences settings;

    //  CL

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //
            Log.d(TAG, "Broadcast Received!!");
            updateData();
        }
    };

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        final int PAGE_COUNT = 3;
        private String tabTitles[] = new String[] { "Detail View", "Summary View", "Add Txn" };

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {

                case 0:
                    detailView = new DetailView();
                    return detailView;
                case 1:
                    summaryView = new SummaryView();
                    return summaryView;
                case 2:
                    addTxn = new AddTxn();
                    return addTxn;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }

    //  FN
    private void updateData() {

        credit = settings.getFloat("credit",0);
        debit = settings.getFloat("debit",0);
        savings = credit - debit;

        textViewExpenses.setText("₹ " + debit + " /-");
        textViewSavings.setText("₹ " + savings + " /-");
        textViewEarnings.setText("₹ " + credit + " /-");
    }

    private void setUpAllFragments() {

        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
    }

    private void setupActionBar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        settings = PreferenceManager.getDefaultSharedPreferences(Dashboard.this);

        //  Initial Setups.
        setupActionBar();

        fab = (FloatingActionButton) findViewById(R.id.fabShowAllTransaction);
        textViewEarnings = (TextView) findViewById(R.id.textViewEarnings);
        textViewSavings = (TextView) findViewById(R.id.textViewSavings);
        textViewExpenses = (TextView) findViewById(R.id.textViewExpenses);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Dashboard.this, Transaction.class);
                startActivity(intent);
            }
        });

        setUpAllFragments();
        updateData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {

        if (mBackPressed + Constants.TIME_INTERVAL_TO_EXIT_APP > System.currentTimeMillis())
            finish();
        else
            Toast.makeText(Dashboard.this, "Tap back again to exit", Toast.LENGTH_SHORT).show();

        mBackPressed = System.currentTimeMillis();
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.COM_ARTOO_FINAC_ADDED_TRANSACTIONS);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

}
