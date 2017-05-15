package com.example.earthshaker.moneybox.common.callback;

/**
 * Created by earthshaker on 14/5/17.
 */


@FunctionalInterface
public interface BaseCallback<T, V> {
    T onResponse(V from);
}


