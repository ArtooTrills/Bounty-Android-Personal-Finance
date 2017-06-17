package com.examples.ankit.breakpoint;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.examples.ankit.breakpoint.models.Transaction;
import com.examples.ankit.breakpoint.utils.DateUtil;
import com.examples.ankit.breakpoint.utils.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ankit on 17/06/17.
 */

public class TransactionDetailsActivity extends AppCompatActivity {
    public final static String TRANSACTION = "transaction";
    private final static String NAME = "name";
    private final static String CATEGORY = "category";
    private final static String DATE = "date";
    private final static String AMOUNT = "amount";
    private Transaction mTransaction;
    @BindView(R.id.fab_transaction_category)
    FloatingActionButton fabCategory;

    @BindView(R.id.txt_transaction_name)
    TextView txtTransactionName;

    @BindView(R.id.txt_transaction_category_name)
    TextView txtTransactionCategory;

    @BindView(R.id.txt_transaction_date)
    TextView txtTransactionDate;

    @BindView(R.id.txt_transaction_amount)
    TextView txtTransactionAmount;

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        if (getIntent() != null && getIntent().hasExtra(TRANSACTION)) {
            mTransaction = Gson.getInstance().fromJson(getIntent().getExtras().getString(TRANSACTION), Transaction.class);
        }
        String[] mCategories = getResources().getStringArray(R.array.expense_category);

        txtTransactionName.setText(mTransaction.getName());
        int expenseOrIncomeCategory = mTransaction.getExpenseOrIncomeCategory();
        if (expenseOrIncomeCategory > 0) {
            txtTransactionCategory.setText(mCategories[expenseOrIncomeCategory]);
        } else {
            txtTransactionCategory.setText("Uncategories");
        }

        txtTransactionDate.setText(DateUtil.dateToString(mTransaction.getDate()));
        txtTransactionAmount.setText(Double.toString(mTransaction.getAmount()));

        fabCategory.setImageResource(UiUtils.getCategoryicon(mTransaction.getExpenseOrIncomeCategory()));
    }

    @OnClick(R.id.close)
    public void onClose(View view) {
        this.finish();
    }

}
