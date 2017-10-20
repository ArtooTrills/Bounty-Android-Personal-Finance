package com.example.nazmuddinmavliwala.ewallet.di.modules;


import com.example.nazmuddinmavliwala.ewallet.data.Executers.BackgroundThread;
import com.example.nazmuddinmavliwala.ewallet.data.Executers.UIThread;
import com.example.nazmuddinmavliwala.ewallet.di.identifiers.ScopedActivity;
import com.example.nazmuddinmavliwala.ewallet.domain.executers.ExecutionThread;
import com.example.nazmuddinmavliwala.ewallet.domain.executers.ExecutionThreadReactivex;
import com.example.nazmuddinmavliwala.ewallet.domain.executers.PostExecutionThread;
import com.example.nazmuddinmavliwala.ewallet.domain.executers.PostExecutionThreadReactiveX;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nazmuddinmavliwala on 12/03/17.
 */

@ScopedActivity
@Module
public class RxModule {

    @Provides
    @ScopedActivity
    public ExecutionThread provideExecutionThread() {
        return new BackgroundThread();
    }

    @ScopedActivity
    @Provides
    public PostExecutionThread providePostExecutionThread() {
        return new UIThread();
    }

}
