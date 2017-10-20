package com.example.nazmuddinmavliwala.ewallet.base.presenters;

import com.example.nazmuddinmavliwala.ewallet.data.exceptions.CandidateException;
import com.example.nazmuddinmavliwala.ewallet.domain.executers.ExecutionThread;
import com.example.nazmuddinmavliwala.ewallet.domain.executers.PostExecutionThread;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by nazmuddinmavliwala on 12/03/17.
 */

public abstract class UseCase<T extends Repository> {

    protected final ExecutionThread executionThread;
    protected final PostExecutionThread postExecutionThread;
    protected final T repository;
    protected Subscription subscriber;
    //protected AnalyticsServiceAJ analyticsService;

    public UseCase(ExecutionThread executionThread,
                   PostExecutionThread postExecutionThread,
                   T repository) {
        this.executionThread = executionThread;
        this.postExecutionThread = postExecutionThread;
        this.repository = repository;
    }

    protected <P>void execute(Observable<P> observable, Subscriber<P> subscriber) {
        this.subscriber = getScheduledObservable(observable)
                .subscribe(subscriber);

    }

    protected <P>void execute(Observable<P> observable, Action1<P> action1) {
        this.subscriber = getScheduledObservable(observable)
                .subscribe(action1);

    }

    protected  <P> Observable<P> getScheduledObservable(Observable<P> observable) {
        return observable.subscribeOn(executionThread.getScheduler())
                .observeOn(postExecutionThread.getScheduler());
    }

    protected <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(executionThread.getScheduler())
                .observeOn(postExecutionThread.getScheduler());
    }


    public void unsubscribe() {
        if (this.subscriber != null) this.subscriber.unsubscribe();
    }

    protected <T> Observable<T> getResponse(Response<T> response) {
        if(response.isSuccessful())
            return Observable.just(response.body());
        else
            return Observable.error(getError(response));

    }

    protected <T> Exception getError(Response<T> response) {
        return new CandidateException();
    }


}
