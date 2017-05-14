package com.example.earthshaker.moneybox.common;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.earthshaker.moneybox.dashboard.activity.DashboardActivity;
import com.example.earthshaker.moneybox.R;

/**
 * Created by earthshaker on 13/5/17.
 */

public abstract class BaseActivity extends AppCompatActivity {


    protected static boolean isLanguageChanged = false;
    public final String TAG = this.getClass().getSimpleName();
    protected Toolbar toolbar;
    protected NavigationView navigationView;
    protected DrawerLayout drawerLayout;
    protected int backPressCount = 0;
    MenuUtils menuUtils;
    DrawerBuilder drawerBuilder;
    BaseActivityNavigators baseActivityNavigator;
    SharedPrefsUtils sharedPrefsUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawers();
            backPressCount = 0;
        } else if (!(this instanceof DashboardActivity)) { //closes other activities
            if (isTaskRoot()) {
                BaseActivityNavigators.openDashboard(this);
                finish();
            } else {
                Log.d(TAG, "dashboardActivity");
                super.onBackPressed();
            }
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    public void setBackPressCount(int backPressCount) {
        this.backPressCount = backPressCount;
    }

    public void onBackArrowUp() {

    }

    protected void setupUI(int layoutId, int viewHolderId) {
        setContentView(layoutId);
        setupViewHolder(findViewById(viewHolderId));
    }

    //initialize the data to the view and other variables
    abstract protected void setupViewHolder(View view);


    protected void initializeChildActivityToolbar(String title) {
        initializeToolbar(title);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            if (isTaskRoot()) {
                BaseActivityNavigators.openDashboard(this);
            }
            finish();
        });
    }

    protected void initializeToolbar(String title) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(0xffffffff);
        setSupportActionBar(toolbar);
    }

    protected void initializeNavigationMenu() {
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //Highlight the first menu item ie "Menu" since by default app will open on Menu screen
        navigationView.setItemTextColor(ColorStateList.valueOf(Color.BLACK));

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        drawerBuilder.build(this, drawerLayout, navigationView);
        /*createDrawerToolbar(toolbar);*/

        setupNavigation();
    }

    /*private void createDrawerToolbar(Toolbar toolbar) {
        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer,
                        R.string.close_drawer) {

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Code here will be triggered once the drawer open as we don't want anything to happen so we
                        // leave this blank
                        super.onDrawerOpened(drawerView);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Code here will be triggered once the drawer closes as we don't want anything to happen so we
                        // leave this blank
                        super.onDrawerClosed(drawerView);
                    }
                };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }*/

    private void setupNavigation() {
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            //Closing drawer on item click
            drawerLayout.closeDrawers();
            return menuUtils.selectMenuItem(menuItem, BaseActivity.this, isTaskRoot());
        });
    }
}
