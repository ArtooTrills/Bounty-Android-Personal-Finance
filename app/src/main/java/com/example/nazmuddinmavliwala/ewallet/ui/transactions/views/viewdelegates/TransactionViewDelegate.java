package com.example.nazmuddinmavliwala.ewallet.ui.transactions.views.viewdelegates;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.nazmuddinmavliwala.ewallet.R;
import com.example.nazmuddinmavliwala.ewallet.base.views.BaseViewDelegate;
import com.example.nazmuddinmavliwala.ewallet.ui.transactions.models.TransactionVO;
import com.example.nazmuddinmavliwala.ewallet.ui.transactions.views.adapters.TransactionAdapter;

import java.security.cert.TrustAnchor;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

/**
 * Created by nazmuddinmavliwala on 01/11/2017.
 */

public class TransactionViewDelegate extends BaseViewDelegate<List<TransactionVO>> {

    @BindView(R.id.rv_transactions)
    RecyclerView recyclerView;

    @Inject
    public TransactionViewDelegate(@NonNull @Named("Activity") Context context,
                                   @NonNull @Named("Data") View view) {
        super(context, view);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this.context);
        TransactionAdapter adapter = new TransactionAdapter(this.context);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void bind(List<TransactionVO> data) {
        List<Object> objects = new ArrayList<>();
        objects.addAll(data);
        ((TransactionAdapter)recyclerView.getAdapter()).append(objects);
    }
}
