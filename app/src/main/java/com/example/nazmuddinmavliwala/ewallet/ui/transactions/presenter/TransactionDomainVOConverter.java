package com.example.nazmuddinmavliwala.ewallet.ui.transactions.presenter;

import com.example.domain.Converter;
import com.example.domain.interactors.Transaction;
import com.example.nazmuddinmavliwala.ewallet.ui.overview.di.ConverterModule;
import com.example.nazmuddinmavliwala.ewallet.ui.transactions.models.TransactionVO;

/**
 * Created by nazmuddinmavliwala on 01/11/2017.
 */

public class TransactionDomainVOConverter implements Converter<Transaction,TransactionVO> {
    @Override
    public TransactionVO convert(Transaction data) {
        return new TransactionVO.TransactionVOBuilder()
                .setAmount(data.getAmount())
                .setTransactionType(data.getTransactionType())
                .setExpenseType(data.getExpenseType())
                .setId(data.getId())
                .createTransactionVO();
    }
}
