package com.example.nazmuddinmavliwala.ewallet;

import android.content.Context;
import android.view.View;

/**
 * Created by nazmuddinmavliwala on 14/03/17.
 */

public abstract class ReactiveViewHolder<T> extends BaseViewHolder<T> {

    protected T data;

    public ReactiveViewHolder(Context context, View itemView) {
        super(context, itemView);
    }

    public Object getCurrentItem() {
        return this.data;
    };

    @Override
    public void bind(T data) {
        this.data = data;
        bindData(data);
    }

    protected boolean isNonNull(Object object){
        if(object==null){
            return false;
        }
        if(object instanceof String){
            if(object.equals("")){
                return false;
            }

        }

        return true;
    }

    protected abstract void bindData(T data);
}
