package com.example.nazmuddinmavliwala.ewallet.base.views;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;

import javax.inject.Named;

import butterknife.ButterKnife;

/**
 * Created by nazmuddinmavliwala on 29/10/2017.
 */

public class BaseViewDelegate<T> implements ViewDelegate<T> {

    @NonNull
    protected final Context context;
    @NonNull
    protected final View view;

    public BaseViewDelegate(@NonNull @Named("Activity") Context context,
                            @NonNull View view) {
        this.context = context;
        this.view = view;
        ButterKnife.bind(this,view);
    }

    @Override
    public void show() {
        this.view.setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        this.view.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void bind(T data) {

    }

    public <T extends View> T findView(@IdRes int view) {
        return ButterKnife.findById(this.view,view);
    }

    protected String getString(@StringRes int resId) {
        return context.getString(resId);
    }
}
