package com.example.nazmuddinmavliwala.ewallet;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by nazmuddinmavliwala on 10/03/17.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    protected final Context context;
    protected final View itemView;

    public View getItemView() {
        return itemView;
    }

    public BaseViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.itemView = itemView;
        ButterKnife.bind(this,itemView);
    }

    public abstract void bind(T data);

    public <T extends View> T findViewById(@IdRes int id) {
        return ButterKnife.findById(itemView,id);
    }

    protected void hideView(@NonNull View view) {
        view.setVisibility(View.GONE);
    }

    protected void hideView(@IdRes int viewId) {
        hideView(findViewById(viewId));
    }

    protected void showView(@NonNull View view) {
        view.setVisibility(View.VISIBLE);
    }

    protected void showView(@IdRes int viewId) {
        showView(findViewById(viewId));
    }
}
