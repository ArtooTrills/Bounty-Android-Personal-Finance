package com.example.nazmuddinmavliwala.ewallet.base.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.nazmuddinmavliwala.ewallet.base.presenters.BaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by nazmuddinmavliwala on 01/06/17.
 */

public abstract class BaseFragment extends Fragment implements BaseView {

    private Unbinder unBinder;
    protected CompositeDisposable disposables = new CompositeDisposable();
    protected CompositeSubscription subscriptions = new CompositeSubscription();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResource(),container,false);
        this.unBinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.unBinder = null;
    }

    protected abstract @LayoutRes
    int getLayoutResource();

    public  <T extends View> T findView(@IdRes int id) {
        //noinspection ConstantConditions
        return ButterKnife.findById(getView(),id);
    }

}
