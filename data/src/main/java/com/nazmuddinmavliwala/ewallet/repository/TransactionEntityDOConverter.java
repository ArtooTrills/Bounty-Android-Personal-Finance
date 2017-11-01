package com.nazmuddinmavliwala.ewallet.repository;

import com.example.domain.Converter;
import com.nazmuddinmavliwala.ewallet.database.entities.TransactionEntity;
import com.nazmuddinmavliwala.ewallet.network.TransactionDO;

/**
 * Created by nazmuddinmavliwala on 01/11/2017.
 */

public class TransactionEntityDOConverter implements Converter<TransactionDO, TransactionEntity> {

    @Override
    public TransactionEntity convert(TransactionDO data) {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(data.getId());
        transactionEntity.setAmount(data.getAmount());
        transactionEntity.setExpenseType(data.getExpenseType());
        transactionEntity.setTransactionType(data.getTrasanctionType());
        return transactionEntity;
    }
}
