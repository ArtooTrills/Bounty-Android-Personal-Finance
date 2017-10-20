package com.example.nazmuddinmavliwala.ewallet.data.interacters;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by nazmuddinmavliwala on 13/01/17.
 */
public abstract class DefaultSubscriber<T> extends rx.Subscriber<Response<T>> {

    @Override
    public void onCompleted() {
        this.unsubscribe();
    }

    @Override
    public void onNext(Response<T> response) {
        if (response.isSuccessful()) {
            onSuccess(response);
        } else {
            ResponseBody error = response.errorBody();
            if (error != null) {
                onError(new Exception());
            }
        }
    }

    protected abstract void onSuccess(Response<T> response);
}
