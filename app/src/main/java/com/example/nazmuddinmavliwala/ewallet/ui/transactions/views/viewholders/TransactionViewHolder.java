package com.example.nazmuddinmavliwala.ewallet.ui.transactions.views.viewholders;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;

import com.example.nazmuddinmavliwala.ewallet.R;
import com.example.nazmuddinmavliwala.ewallet.base.views.ReactiveViewHolder;
import com.example.nazmuddinmavliwala.ewallet.ui.transactions.models.TransactionVO;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindViews;

/**
 * Created by nazmuddinmavliwala on 01/11/2017.
 */

public class TransactionViewHolder extends ReactiveViewHolder<TransactionVO> {

    private static final int CREDIT = 1;
    private static final int DEBIT = 0;

    @BindViews({
            R.id.tv_expense_type,
            R.id.tv_transaction_type,
            R.id.tv_value
    })
    List<TextView> textViews;

    @BindDrawable(R.drawable.ic_chevron_top)
    Drawable top;

    @BindDrawable(R.drawable.ic_chevron_bottom)
    Drawable bottom;

    @BindColor(R.color.redDark)
    int red;

    @BindColor(R.color.oliveGreenDark)
    int green;

    public TransactionViewHolder(Context context, View itemView) {
        super(context, itemView);
    }

    @Override
    protected void bindData(TransactionVO data) {
        TextView tvTransactionType = findViewById(R.id.tv_transaction_type);
        int transactionType = data.getTransactionType();
        if (transactionType == CREDIT) {
            tvTransactionType.setTextColor(green);
            tvTransactionType.setText("DT");
            tvTransactionType.setCompoundDrawablesWithIntrinsicBounds(null,null,top,null);
        } else {
            tvTransactionType.setTextColor(red);
            tvTransactionType.setText("CR");
            tvTransactionType.setCompoundDrawablesWithIntrinsicBounds(null,null,bottom,null);
        }

        TextView tvExpenseType = findViewById(R.id.tv_expense_type);
        tvExpenseType.setText(data.getExpenseType());

        TextView tvAmount = findViewById(R.id.tv_value);
        tvAmount.setText(String.valueOf(data.getAmount()));
    }
}
