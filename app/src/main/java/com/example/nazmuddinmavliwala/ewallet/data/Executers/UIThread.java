package com.example.nazmuddinmavliwala.ewallet.data.Executers;

import com.example.nazmuddinmavliwala.ewallet.domain.executers.PostExecutionThread;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by nazmuddinmavliwala on 20/01/17.
 */

public class UIThread implements PostExecutionThread {

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
