package com.example.domain.executers;

import rx.Scheduler;

/**
 * Created by nazmuddinmavliwala on 20/01/17.
 */

public interface ExecutionThread {
    Scheduler getScheduler();
}
