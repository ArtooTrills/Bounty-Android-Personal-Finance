package com.nazmuddinmavliwala.ewallet.network;


import java.util.List;
import java.util.Observable;

/**
 * Created by nazmuddinmavliwala on 29/10/2017.
 */

public interface NetworkService {

    rx.Observable<List<TransactionDO>> getTransactions();
}
