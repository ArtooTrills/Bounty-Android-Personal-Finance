package bounty.android.example.com.bounty;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static MainActivity inst;

    public static MainActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    public void updateList(final String str) {
        MyDBHandlerSms dbHandler = new MyDBHandlerSms(this, null, null, 1);

        String[] list = str.split(":");

        Sms newSms = new Sms();
        newSms.setFrom(list[1]);

        if(list[2].contains("credit"))
            newSms.setType("Income");
        else if(list[2].contains("debit"))
            newSms.setType("Expense");
        else
            return;

        String[] amt = list[2].split("Rs.");
        amt = amt[1].split(" ");
        newSms.setAmount(amt[1]);

        dbHandler.addSms(newSms);
    }
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    public static Context mContext;
    private Switch mSms;
    private WelcomeActivity ft = new WelcomeActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        mContext = this;

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();

        if(position == 0) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, ft)
                    .commit();
/*
            MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

            Total_ t = new Total_(58, 69);
            dbHandler.addTotal(t);

            Double[] totalList = dbHandler.findTotal();

            ((TextView) findViewById(R.id.income_value)).setText(totalList[0].toString());
            ((TextView) findViewById(R.id.expense_value)).setText(totalList[1].toString());
            */

        }
        else
            fragmentManager.beginTransaction()
                    .replace(R.id.container,PlaceholderFragment.newInstance(position + 1))
                    .commit();
    }

    public void onClick(View v){
        ft.onClick(v);
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private int section = 0;
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            Bundle args = this.getArguments();
            int sec = args.getInt(ARG_SECTION_NUMBER);
            View rootView;

            switch(sec){
                case 1:
                    rootView = inflater.inflate(R.layout.welcome_page, container, false);
                    break;
                case 2:
                    Intent incomeIntent = new Intent(mContext, IncomeActivity.class);
                    mContext.startActivity(incomeIntent);
                    break;
                case 3:
                    Intent expenseIntent = new Intent(mContext, ExpenseActivity.class);
                    mContext.startActivity(expenseIntent);
                    break;
                case 4 :
                    Intent smsIntent = new Intent(mContext, SmsActivity.class);
                    mContext.startActivity(smsIntent);
                    break;
            }
            rootView = inflater.inflate(R.layout.welcome_page, container, false);

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
