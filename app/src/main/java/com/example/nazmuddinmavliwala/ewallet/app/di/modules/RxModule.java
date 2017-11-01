package com.example.nazmuddinmavliwala.ewallet.app.di.modules;


import com.example.domain.executers.ExecutionThread;
import com.example.domain.executers.PostExecutionThread;
import com.example.nazmuddinmavliwala.ewallet.app.di.identifiers.ScopedActivity;
import com.nazmuddinmavliwala.ewallet.interactors.BackgroundThread;
import com.nazmuddinmavliwala.ewallet.interactors.UIThread;

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
