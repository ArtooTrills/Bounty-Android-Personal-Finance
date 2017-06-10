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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by earthshaker on 13/5/17.
 */

public abstract class BaseActivity extends AppCompatActivity {


    public final String TAG = this.getClass().getSimpleName();
    protected Toolbar toolbar;
    protected NavigationView navigationView;
    protected DrawerLayout drawerLayout;
    protected int backPressCount = 0;

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
        getIntents();
        setupViewHolder(findViewById(viewHolderId));
    }

    //Receives the intent values for this activity
    protected void getIntents() {

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
        DrawerBuilder.build(this, drawerLayout, navigationView);

        setupNavigation();
    }

    private void setupNavigation() {
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            //Closing drawer on item click
            drawerLayout.closeDrawers();
            return MenuUtils.selectMenuItem(menuItem, BaseActivity.this, isTaskRoot());
        });
    }
}
