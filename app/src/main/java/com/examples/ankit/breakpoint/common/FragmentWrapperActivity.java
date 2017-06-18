package com.examples.ankit.breakpoint.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.examples.ankit.breakpoint.AddExpenseFragment;
import com.examples.ankit.breakpoint.Gson;
import com.examples.ankit.breakpoint.R;
import com.examples.ankit.breakpoint.TransactionDetailsActivity;
import com.examples.ankit.breakpoint.models.Transaction;

public class FragmentWrapperActivity extends AppCompatActivity implements AddExpenseFragment.OnAddExpenseListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_wrapper_activity);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            Fragment fragment = getFragment();
            fragment.setArguments(getIntent().getExtras());

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public Fragment getFragment() {
        return AddExpenseFragment.getInstance(getIntent().getExtras().getString(TransactionDetailsActivity.TRANSACTION));
    }

    @Override
    public void onAddExpense(Transaction transaction) {
        Intent intent = new Intent();
        intent.putExtra(TransactionDetailsActivity.TRANSACTION, Gson.getInstance().toJson(transaction));
        setResult(RESULT_OK, intent);
        this.finish();
    }
}