package com.example.nazmuddinmavliwala.ewallet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by nazmuddinmavliwala on 26/12/15.
 */
public abstract class AbstractAdapterDelegate<T> implements AdapterDelegate<T> {
    protected final Context context;
    protected int viewType;

    public AbstractAdapterDelegate(int viewType, Context context) {
        this.viewType = viewType;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(getViewHolderLayout(), parent, false);
        return getViewHolder(itemView);
    }

    protected abstract RecyclerView.ViewHolder getViewHolder(View itemView);

    protected abstract int getViewHolderLayout();

    @Override
    public int getItemViewType() {
        return viewType;
    }

}
