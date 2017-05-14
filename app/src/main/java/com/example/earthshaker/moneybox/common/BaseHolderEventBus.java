package com.example.earthshaker.moneybox.common;

import de.greenrobot.event.EventBus;

/**
 * Created by earthshaker on 14/5/17.
 */

public abstract class BaseHolderEventBus {

    public void registerEventBus(int priority) {
        EventBus.getDefault().register(this, priority);
    }

    public void unRegisterEventBus() {
        EventBus.getDefault().unregister(this);
    }

    protected abstract void refreshData();

    protected abstract void recreateLayout();
}
