package com.example.nazmuddinmavliwala.ewallet.ui.transactions.views.adapterdelegates;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.nazmuddinmavliwala.ewallet.AbstractAdapterDelegate;
import com.example.nazmuddinmavliwala.ewallet.R;

import java.util.List;

/**
 * Created by nazmuddinmavliwala on 19/10/2017.
 */

public class DebitDelegate extends AbstractAdapterDelegate<List<Object>> {
    public DebitDelegate(int viewType, Context context) {
        super(viewType, context);
    }

    @Override
    public boolean isForViewType(@NonNull List<Object> items, int position) {
        return false;
    }

    @Override
    public void onBindViewHolder(@NonNull List<Object> items, int position, @NonNull RecyclerView.ViewHolder holder) {

    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(View itemView) {
        return null;
    }

    @Override
    protected int getViewHolderLayout() {
        return R.layout.debit_row;
    }
}
