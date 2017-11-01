package com.example.nazmuddinmavliwala.ewallet.ui.overview.views.viewdelegates;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.nazmuddinmavliwala.ewallet.base.views.BaseViewDelegate;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by nazmuddinmavliwala on 01/11/2017.
 */

public class ErrorViewDelegate extends BaseViewDelegate {

    @Inject
    public ErrorViewDelegate(@NonNull @Named("Activity") Context context,
                             @NonNull @Named("Error") View view) {
        super(context, view);
    }
}
