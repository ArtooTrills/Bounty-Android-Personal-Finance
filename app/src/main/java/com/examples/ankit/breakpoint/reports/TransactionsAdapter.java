package com.examples.ankit.breakpoint.reports;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.examples.ankit.breakpoint.R;
import com.examples.ankit.breakpoint.models.Transaction;
import com.examples.ankit.breakpoint.utils.DateUtil;

import java.util.List;

/**
 * Created by ankit on 23/05/17.
 */

public class TransactionsAdapter extends BaseAdapter {
    private static final String TAG = "TA";
    private Activity mActivity;
    private List<Transaction> mTransactions;
    private double total;
    private String[] mCategories;

    public TransactionsAdapter(Activity activity, List<Transaction> transactions) {
        this.mActivity = activity;
        this.mTransactions = transactions;
        mCategories = activity.getResources().getStringArray(R.array.expense_category);
    }

    public void setTransactions(List<Transaction> mTransactions) {
        this.mTransactions = mTransactions;
    }

    @Override
    public int getCount() {
        return mTransactions != null ? mTransactions.size() : 0;
    }

    @Override
    public Transaction getItem(int i) {
        return mTransactions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if(convertView == null) {
            convertView = mActivity.getLayoutInflater().inflate(R.layout.transaction_row_item, null);
            holder = new ViewHolder();
            holder.transactionAmount = (TextView) convertView.findViewById(R.id.txt_transaction_amount);
            holder.transactionName = (TextView) convertView.findViewById(R.id.txt_transaction_name);
            holder.transactionDate = (TextView) convertView.findViewById(R.id.txt_transaction_date);
            holder.transactionCategory = (TextView) convertView.findViewById(R.id.txt_transaction_category);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Transaction transaction = getItem(position);

        holder.transactionName.setText(transaction.getName());
        holder.transactionAmount.setText(Double.toString(transaction.getAmount()));
        holder.transactionDate.setText(DateUtil.dateToString(transaction.getDate()));
        int expenseOrIncomeCategory = transaction.getExpenseOrIncomeCategory();
        if(expenseOrIncomeCategory > 0) {
            holder.transactionCategory.setText(mCategories[expenseOrIncomeCategory]);
        } else {
            holder.transactionCategory.setText("Uncategories");
        }
        return convertView;
    }

    static class ViewHolder {
        private TextView transactionAmount;
        private TextView transactionName;
        private TextView transactionDate;
        private TextView transactionCategory;
    }
}
