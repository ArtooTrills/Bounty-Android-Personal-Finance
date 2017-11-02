package com.example.nazmuddinmavliwala.ewallet.base.views;

import android.content.Intent;

import java.util.List;

/**
 * Created by nazmuddinmavliwala on 28/10/2017.
 */

public class ViewManager<T> {


    private List<ViewDelegate<T>> viewDelegates;

    public ViewManager(List<ViewDelegate<T>> viewDelegates) {
        this.viewDelegates = viewDelegates;
    }

    public <P extends ViewDelegate> void showExplicit(Class<P> clazz) {
        for (ViewDelegate viewDelegate : viewDelegates) {
            if (clazz.isAssignableFrom(viewDelegate.getClass())) viewDelegate.show();
            else viewDelegate.hide();
        }
    }

    public void showAll() {
        for (ViewDelegate<T> viewDelegate : viewDelegates) {
            viewDelegate.show();
        }
    }

    public void hideAll() {
        for (ViewDelegate<T> viewDelegate : viewDelegates) {
            viewDelegate.hide();
        }
    }


    public <P extends ViewDelegate> void hideSpecific(Class<P> clazz) {
        for (ViewDelegate viewDelegate : viewDelegates) {
            if (clazz.isAssignableFrom(viewDelegate.getClass())) viewDelegate.hide();
        }
    }

    public <P extends ViewDelegate<T>> P getViewDelegate(Class<P> clazz) {
        for (ViewDelegate<T> viewDelegate : viewDelegates) {
            if (clazz.isAssignableFrom(viewDelegate.getClass())) {
                //noinspection unchecked
                return (P) viewDelegate;
            }
        }
        throw new IllegalArgumentException("No viewdelegate found for class - "
                + clazz.getSimpleName());
    }

    public void bind(T data) {
        for (ViewDelegate<T> viewDelegate : viewDelegates) {
            viewDelegate.bind(data);
        }
    }

    public void onCreate() {
        for (ViewDelegate<T> viewDelegate : viewDelegates) {
            viewDelegate.onCreate();
        }
    }

    public void onResume() {
        for (ViewDelegate<T> viewDelegate : viewDelegates) {
            viewDelegate.onResume();
        }
    }

    public void onPause() {
        for (ViewDelegate<T> viewDelegate : viewDelegates) {
            viewDelegate.onPause();
        }
    }

    public void onStop() {
        for (ViewDelegate<T> viewDelegate : viewDelegates) {
            viewDelegate.onStop();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (ViewDelegate<T> viewDelegate : viewDelegates) {
            viewDelegate.onActivityResult(requestCode, resultCode, data);
        }
    }
}
