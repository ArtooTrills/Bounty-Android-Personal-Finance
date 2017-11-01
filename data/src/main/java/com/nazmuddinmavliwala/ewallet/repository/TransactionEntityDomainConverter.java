package com.nazmuddinmavliwala.ewallet.repository;

import com.example.domain.Converter;
import com.example.domain.interactors.Transaction;
import com.nazmuddinmavliwala.ewallet.database.entities.TransactionEntity;

/**
 * Created by nazmuddinmavliwala on 01/11/2017.
 */

public class TransactionEntityDomainConverter implements Converter<TransactionEntity, Transaction> {

    @Override
    public Transaction convert(TransactionEntity data) {
        return new Transaction.TransactionBuilder()
                .setId(data.getId())
                .setExpense(data.getAmount())
                .setExpenseType(data.getExpenseType())
                .setTransactionType(data.getTransactionType())
                .createTransaction();
    }
}
