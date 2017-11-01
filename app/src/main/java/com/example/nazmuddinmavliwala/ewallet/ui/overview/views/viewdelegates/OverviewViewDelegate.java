package com.example.nazmuddinmavliwala.ewallet.ui.overview.views.viewdelegates;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.nazmuddinmavliwala.ewallet.R;
import com.example.nazmuddinmavliwala.ewallet.base.views.BaseViewDelegate;
import com.example.nazmuddinmavliwala.ewallet.ui.overview.models.TransactionsVO;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindViews;

/**
 * Created by nazmuddinmavliwala on 01/11/2017.
 */

public class OverviewViewDelegate extends BaseViewDelegate<TransactionsVO> {

    @BindViews({
            R.id.tv_credit,
            R.id.tv_debit
    })
    List<TextView> textViews;

    @Inject
    public OverviewViewDelegate(@NonNull @Named("Activity") Context context,
                                @NonNull @Named("Data") View view) {
        super(context, view);
    }

    @Override
    public void bind(TransactionsVO data) {
        super.bind(data);
        TextView tvCredit = findView(R.id.tv_credit);
        tvCredit.setText("Credit - " + String.valueOf(data.getCredit()));

        TextView tvDebit = findView(R.id.tv_debit);
        tvDebit.setText("Debit - " + String.valueOf(data.getCredit()));


    }
}
