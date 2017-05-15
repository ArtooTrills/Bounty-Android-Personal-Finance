package com.example.earthshaker.moneybox.common.callback;

/**
 * Created by earthshaker on 14/5/17.
 */

public interface ReturnWithParameterCallback<T, V> {
    T onResponse(V v);
}

