package com.example.nazmuddinmavliwala.ewallet.data.Executers;

import com.example.nazmuddinmavliwala.ewallet.domain.executers.ExecutionThread;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by nazmuddinmavliwala on 20/01/17.
 */

public class BackgroundThread implements ExecutionThread {
    @Override
    public Scheduler getScheduler() {
        return Schedulers.newThread();
    }
}
