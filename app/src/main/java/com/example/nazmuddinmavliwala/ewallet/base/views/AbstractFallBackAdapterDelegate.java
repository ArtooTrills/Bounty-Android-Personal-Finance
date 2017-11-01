package com.example.nazmuddinmavliwala.ewallet.base.views;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by nazmuddinmavliwala on 26/12/15.
 */
public abstract class AbstractFallBackAdapterDelegate<T> extends AbstractAdapterDelegate<T> {

    public AbstractFallBackAdapterDelegate(int viewType, Context context) {
        super(viewType, context);
    }

    @Override
    public boolean isForViewType(@NonNull Object items, int position) {
        return true;
    }
}
