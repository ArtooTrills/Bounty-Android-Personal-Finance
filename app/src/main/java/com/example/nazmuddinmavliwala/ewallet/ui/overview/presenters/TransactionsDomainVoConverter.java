package com.example.nazmuddinmavliwala.ewallet.ui.overview.presenters;

import com.example.domain.Converter;
import com.example.domain.interactors.Transactions;
import com.example.nazmuddinmavliwala.ewallet.ui.overview.models.TransactionsVO;

/**
 * Created by nazmuddinmavliwala on 01/11/2017.
 */

public class TransactionsDomainVoConverter implements Converter<Transactions,TransactionsVO> {

    @Override
    public TransactionsVO convert(Transactions data) {
        return new TransactionsVO(data.getCredit(),data.getDebit());
    }
}
