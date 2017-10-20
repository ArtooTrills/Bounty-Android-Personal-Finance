package com.example.nazmuddinmavliwala.ewallet.ui.reports.di;

import com.example.nazmuddinmavliwala.ewallet.ui.reports.views.ReportsFragment;
import com.example.nazmuddinmavliwala.ewallet.di.components.ApplicationComponent;
import com.example.nazmuddinmavliwala.ewallet.di.identifiers.ScopedActivity;
import com.example.nazmuddinmavliwala.ewallet.di.modules.RxModule;

import dagger.Component;

/**
 * Created by nazmuddinmavliwala on 20/10/2017.
 */

@ScopedActivity
@Component(
        modules = {
                RxModule.class,
                ResportsModule.class
        },
        dependencies = ApplicationComponent.class
)
public interface ReportsComponent {
    void inject(ReportsFragment fragment);
}
