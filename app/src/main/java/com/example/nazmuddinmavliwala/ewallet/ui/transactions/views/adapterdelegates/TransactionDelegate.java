package com.example.nazmuddinmavliwala.ewallet.ui.transactions.views.adapterdelegates;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.nazmuddinmavliwala.ewallet.R;
import com.example.nazmuddinmavliwala.ewallet.base.views.AbstractAdapterDelegate;
import com.example.nazmuddinmavliwala.ewallet.ui.transactions.models.TransactionVO;
import com.example.nazmuddinmavliwala.ewallet.ui.transactions.views.viewholders.TransactionViewHolder;

import java.util.List;

/**
 * Created by nazmuddinmavliwala on 01/11/2017.
 */

public class TransactionDelegate extends AbstractAdapterDelegate<List<Object>> {

    public TransactionDelegate(int viewType, Context context) {
        super(viewType, context);
    }

    @Override
    public boolean isForViewType(@NonNull List<Object> items, int position) {
        return items.get(position) instanceof TransactionVO;
    }

    @Override
    public void onBindViewHolder(@NonNull List<Object> items, int position, @NonNull RecyclerView.ViewHolder holder) {
        ((TransactionViewHolder)holder).bind((TransactionVO) items.get(position));
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(View itemView) {
        return new TransactionViewHolder(this.context,itemView);
    }

    @Override
    protected int getViewHolderLayout() {
        return R.layout.transaction_row;
    }
}
