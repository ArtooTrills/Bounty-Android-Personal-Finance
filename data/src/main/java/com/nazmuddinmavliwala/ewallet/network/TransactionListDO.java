package com.nazmuddinmavliwala.ewallet.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nazmuddinmavliwala on 01/11/2017.
 */

public class TransactionListDO {

    @SerializedName("transactions")
    private List<TransactionDO> transactionDOS;

    public List<TransactionDO> getTransactionDOS() {
        return transactionDOS;
    }

    public void setTransactionDOS(List<TransactionDO> transactionDOS) {
        this.transactionDOS = transactionDOS;
    }
}
