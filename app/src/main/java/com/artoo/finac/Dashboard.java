package com.artoo.finac;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity {

    //  DM
    private long mBackPressed;

    private Fragment detailView;
    private Fragment summaryView;
    private Fragment addTxn;

    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    //  CL
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

        //  Initial Setups.
        setupActionBar();

        setUpAllFragments();

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

}
