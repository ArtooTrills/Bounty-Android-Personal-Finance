package com.example.nazmuddinmavliwala.ewallet.base.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by nazmuddinmavliwala on 26/12/15.
 */
public abstract class ReactiveRecyclerAdapter extends RecyclerView.Adapter {

    public final Context context;
    public List<Object> items = new LinkedList<>();
    public AdapterDelegateManager<List<Object>> delegatesManager;
    protected final PublishSubject<Object> viewClickSubject = PublishSubject.create();
    protected final io.reactivex.subjects.PublishSubject<Object> reactiveViewClick = io.reactivex.subjects.PublishSubject.create();

    public Observable<Object> getViewClickedObservable() {
        return this.viewClickSubject;
    }

    @SuppressWarnings("unchecked")
    public ReactiveRecyclerAdapter(Context context) {
        this.context = context;
        this.delegatesManager = new AdapterDelegateManager<>();
        List<AdapterDelegate> adapterDelegates = initAdapterDelegates();
        for (AdapterDelegate adapterDelegate : adapterDelegates) {
            this.delegatesManager.addDelegate(adapterDelegate);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ReactiveViewHolder viewHolder =
                (ReactiveViewHolder) delegatesManager.onCreateViewHolder(parent, viewType);
        RxView.clicks(viewHolder.getItemView())
                .takeUntil(RxView.detaches(parent))
                .map(o -> viewHolder.getCurrentItem())
                .subscribe(o -> {
                    viewClickSubject.onNext(o);
                    reactiveViewClick.onNext(o);
                });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        delegatesManager.onBindViewHolder(items, position, holder);
    }

    @Override
    public int getItemViewType(int position) {
        return delegatesManager.getItemViewType(items, position);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void append(List<Object> items) {
        if (this.items.size() == 0) {
            this.items.addAll(items);
            notifyDataSetChanged();
        } else {
            int offset = this.items.size();
            this.items.addAll(items);
            notifyItemRangeInserted(offset,items.size());
        }
    }

    protected abstract List<AdapterDelegate> initAdapterDelegates();

    public void clear() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public void setFallBackDelegate(AdapterDelegate<List<Object>> adapterDelegate) {
        this.delegatesManager.setFallbackDelegate(adapterDelegate);
    }

    public io.reactivex.Observable<Object> getReactiveViewClickObservable() {
        return this.reactiveViewClick;
    }
}
