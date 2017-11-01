package com.nazmuddinmavliwala.ewallet.interactors;

import com.example.domain.executers.ExecutionThread;

import javax.inject.Inject;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by nazmuddinmavliwala on 29/10/2017.
 */

public class BackgroundThread implements ExecutionThread {

    @Inject
    public BackgroundThread() {
    }

    @Override
    public Scheduler getScheduler() {
        return Schedulers.newThread();
    }
}
