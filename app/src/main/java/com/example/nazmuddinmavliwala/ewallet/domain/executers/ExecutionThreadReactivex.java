package com.example.nazmuddinmavliwala.ewallet.domain.executers;

import io.reactivex.Scheduler;

/**
 * Created by ajmac1005 on 01/08/17.
 */

public interface ExecutionThreadReactivex {
    Scheduler getScheduler();
}
