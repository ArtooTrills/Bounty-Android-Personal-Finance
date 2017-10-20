package com.example.nazmuddinmavliwala.ewallet.domain.executers;

import rx.Scheduler;

/**
 * Created by nazmuddinmavliwala on 20/01/17.
 */

public interface PostExecutionThread {
    Scheduler getScheduler();
}
