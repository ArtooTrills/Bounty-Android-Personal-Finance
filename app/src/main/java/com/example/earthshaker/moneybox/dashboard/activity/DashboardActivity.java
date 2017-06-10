package com.example.earthshaker.moneybox.dashboard.activity;


import android.app.ProgressDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.earthshaker.moneybox.R;
import com.example.earthshaker.moneybox.budget.eventbus.BudgteEventBus;
import com.example.earthshaker.moneybox.common.ActivityNavigator;
import com.example.earthshaker.moneybox.common.BaseActivity;
import com.example.earthshaker.moneybox.common.TwoButtonDialogListener;
import com.example.earthshaker.moneybox.common.TwoButtonSimpleDialog;
import com.example.earthshaker.moneybox.common.callback.ReturnWithParameterCallback;
import com.example.earthshaker.moneybox.common.dao.CursorHelper;
import com.example.earthshaker.moneybox.common.eventbus.CommonEvents;
import com.example.earthshaker.moneybox.dashboard.SmsProcessor;
import com.example.earthshaker.moneybox.dashboard.eventbus.DashboardEventBus;
import com.example.earthshaker.moneybox.transaction.eventbus.TransactionsEventBus;

import java.util.List;

import de.greenrobot.event.EventBus;

public class DashboardActivity extends BaseActivity {

    DashboardViewHolder dashboardViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUI(R.layout.activity_dashboard, R.id.parent_coordinator_layout);
        EventBus.getDefault().register(this, getResources().getInteger(R.integer.level_0));
    }


    @Override
    protected void setupViewHolder(View view) {
        initializeToolbar("Dashboard");
        initializeNavigationMenu();
        dashboardViewHolder = new DashboardViewHolder(this, view);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(DashboardEventBus.SyncSms syncMsg) {
        new TwoButtonSimpleDialog(this, "Grant Permission",
                "Press Ok to let us scan your SMS", "Proceed",
                "Cancel", new TwoButtonDialogListener() {
            @Override
            public void onAccept() {
                new AsyncCaller().execute();
            }

            @Override
            public void onReject() {

            }
        });
    }

    public class AsyncCaller extends AsyncTask<Void, Void, Void> {
        ProgressDialog pdLoading = new ProgressDialog(DashboardActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tSyncing...");
            pdLoading.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            parseSms();
            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //this method will be running on UI thread

            pdLoading.dismiss();
        }

    }

    private void parseSms() {
        iterateContent();

    }


    public void iterateContent() {
        try {
            Cursor cursor = getContentResolver().query(Uri.parse(getString(R.string.sms_inbox_uri)), null, null, null, null);
            CursorHelper.iterateCursor(cursor);
            EventBus.getDefault().post(new DashboardEventBus.RefreshDashboard());
        } catch (Exception e) {
            Log.e("ContentResolverHelper ", e.getMessage());
        }
    }

    public static void extractFromCursor(Cursor c) {
        String body = c.getString(c.getColumnIndex("body"));
        long time = c.getLong(c.getColumnIndex("date"));
        if (body.contains("debit")) {
            DashboardHelper.createTransaction(true, body,time);
        } else if (body.contains("credit")) {
            DashboardHelper.createTransaction(false, body,time);
        }
    }

    public void onEventMainThread(DashboardEventBus.RefreshDashboard event) {
        dashboardViewHolder.refreshData();
    }

    public void onEventMainThread(TransactionsEventBus.OpenTransaction event) {
        ActivityNavigator.openTransactionActivity(this, event.getTransactionConfig());
    }

    public void onEventMainThread(BudgteEventBus.OpenBudget event) {
        ActivityNavigator.openBudgetActivity(this);
    }

    @Override
    public void onBackPressed() {
        DashboardFabViewHolder dashboardFabViewHolder = dashboardViewHolder.getFabBuilder();
        if (dashboardFabViewHolder != null && dashboardFabViewHolder.isFabOpen()) {
            dashboardFabViewHolder.closeFab();
            return;
        }
        super.onBackPressed();
    }

    public void onEventMainThread(CommonEvents.StartAddingBudget event) {
        ActivityNavigator.openBudgetActivity(this);
    }

    public void onEventMainThread(CommonEvents.StartAddingTransaction event) {
        ActivityNavigator.openTransactionActivity(this, null);

    }

    public void onEventMainThread(CommonEvents.AddTransaction event) {
        dashboardViewHolder.refreshData();
    }

    public void onEventMainThread(CommonEvents.AddBudget event){
        dashboardViewHolder.refreshData();
    }

    public void onEventMainThread(TransactionsEventBus.OpenAllTxns event) {
        ActivityNavigator.openTransactionLIstActivity(this);
    }

    public void onEventMainThread(CommonEvents.BudgetModifiedEvent event){
        dashboardViewHolder.refreshData();
    }
}
