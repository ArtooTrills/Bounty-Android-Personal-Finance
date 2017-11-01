package com.example.nazmuddinmavliwala.ewallet.ui.overview.di;

import com.example.nazmuddinmavliwala.ewallet.ui.overview.views.fragments.OverviewFragment;
import com.example.nazmuddinmavliwala.ewallet.app.di.components.ApplicationComponent;
import com.example.nazmuddinmavliwala.ewallet.app.di.identifiers.ScopedActivity;
import com.example.nazmuddinmavliwala.ewallet.app.di.modules.RxModule;

import dagger.Component;

/**
 * Created by nazmuddinmavliwala on 20/10/2017.
 */

@ScopedActivity
@Component(
        dependencies = ApplicationComponent.class,
        modules = {
                RxModule.class,
                OverviewModule.class,
                ConverterModule.class
        }
)
public interface OverviewComponent {
    void inject(OverviewFragment fragment);
}
