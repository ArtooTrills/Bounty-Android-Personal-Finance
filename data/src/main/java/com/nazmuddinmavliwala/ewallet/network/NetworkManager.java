package com.nazmuddinmavliwala.ewallet.network;

import android.content.Context;

import com.google.gson.Gson;
import com.nazmuddinmavliwala.ewallet.utils.FileUtils;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by nazmuddinmavliwala on 29/10/2017.
 */


@Singleton
public class NetworkManager implements NetworkService {

    private static final String FILE_NAME = "data.json";
    private Context context;
    private Gson gson;


    @Inject
    public NetworkManager(@Named("Application") Context context,
                          Gson gson) {
        this.context = context;
        this.gson = gson;
    }

    @Override
    public Observable<List<TransactionDO>> getTransactions() {
        String file = FileUtils.loadJSONFromAsset(this.context, FILE_NAME);
        TransactionListDO listDO = this.gson.fromJson(file, TransactionListDO.class);
        return Observable.just(listDO.getTransactionDOS());
    }
}
