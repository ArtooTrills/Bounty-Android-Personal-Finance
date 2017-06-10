package com.example.earthshaker.moneybox.transaction.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.earthshaker.moneybox.R;
import com.example.earthshaker.moneybox.common.CategoryUtils;
import com.example.earthshaker.moneybox.common.callback.ParameterCallback;
import com.example.earthshaker.moneybox.transaction.TransactionConfig;
import com.example.earthshaker.moneybox.transaction.eventbus.TransactionsEventBus;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by earthshaker on 15/5/17.
 */

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.TransactionListHolder> {

    private List<TransactionConfig> mTransactionList;
    Context mContext;

    public TransactionListAdapter(Context context, List<TransactionConfig> itemView) {
        mContext = context;
        mTransactionList = itemView;
    }

    @Override
    public TransactionListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_transaction_list, parent, false);
        return new TransactionListHolder(view);
    }

    @Override
    public void onBindViewHolder(TransactionListHolder holder, int position) {
        TransactionConfig transactionConfig = mTransactionList.get(position);
        holder.attachListeners(mTransactionList.get(position), this::onCardClick);
        holder.setDataOnViews(transactionConfig);
    }

    @Override
    public int getItemCount() {
        return mTransactionList.size();
    }

    private void onCardClick(TransactionConfig transactionConfig) {
        if (mTransactionList != null && mTransactionList.size() > 0) {
            EventBus.getDefault().post(new TransactionsEventBus.OpenTransaction(transactionConfig));
        }
    }

    public void setData(List<TransactionConfig> displayedTransactions) {
        this.mTransactionList = displayedTransactions;
        notifyDataSetChanged();
    }

    public class TransactionListHolder extends RecyclerView.ViewHolder {

        private TextView categoryName, amount;
        private CardView cardView;
        private ImageView categoryLogo;

        public TransactionListHolder(View itemView) {
            super(itemView);
            categoryLogo = (ImageView) itemView.findViewById(R.id.categoryIcon);
            categoryName = (TextView) itemView.findViewById(R.id.categoryName);
            amount = (TextView) itemView.findViewById(R.id.amount);
            cardView = (CardView) itemView.findViewById(R.id.cv_transaction);
        }

        void attachListeners(TransactionConfig transactionConfig,
                             ParameterCallback<TransactionConfig> callback) {
            cardView.setOnClickListener(l -> {
                callback.onResponse(transactionConfig);
            });
        }

        public void setDataOnViews(TransactionConfig transactionConfig) {
            Picasso.with(mContext)
                    .load(CategoryUtils.getCategoryIcon(transactionConfig.getCategory()))
                    .placeholder(R.drawable.background_fill)
                    .into(categoryLogo);

            categoryName.setText(transactionConfig.getCategory());
            amount.setText(transactionConfig.getAmount().toString());
            if (transactionConfig.getExpense())
                amount.setTextColor(Color.RED);
            else
                amount.setTextColor(Color.GREEN);
        }
    }
}
