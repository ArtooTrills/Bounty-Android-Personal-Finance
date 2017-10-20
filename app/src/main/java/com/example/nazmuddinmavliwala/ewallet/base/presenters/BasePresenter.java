package com.example.nazmuddinmavliwala.ewallet.base.presenters;


/**
 * Created by nazmuddinmavliwala on 23/01/17.
 */
public abstract class BasePresenter<V> implements Presenter {

    protected final V view;

    public BasePresenter(V view) {
        this.view = view;
    }

}
