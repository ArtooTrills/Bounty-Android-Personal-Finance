package com.example.earthshaker.moneybox.transaction.eventbus;

import com.example.earthshaker.moneybox.transaction.TransactionConfig;

/**
 * Created by earthshaker on 15/5/17.
 */

public class TransactionsEventBus {

    public static class OpenTransaction {
        TransactionConfig transactionConfig;

        public TransactionConfig getTransactionConfig() {
            return transactionConfig;
        }

        public OpenTransaction(TransactionConfig transactionConfig) {

            this.transactionConfig = transactionConfig;
        }
    }

    public static class OpenAllTxns

    {
    }

    public static class OpenCategoryActivity {
    }
}
