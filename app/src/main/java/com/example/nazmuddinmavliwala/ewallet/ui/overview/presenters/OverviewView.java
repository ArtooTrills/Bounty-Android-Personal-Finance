package com.example.nazmuddinmavliwala.ewallet.ui.overview.presenters;

import com.example.domain.interactors.Transactions;
import com.example.nazmuddinmavliwala.ewallet.base.presenters.BaseView;
import com.example.nazmuddinmavliwala.ewallet.ui.overview.models.TransactionsVO;

/**
 * Created by nazmuddinmavliwala on 20/10/2017.
 */

public interface OverviewView extends BaseView {
    void showErrorView();

    void showDataView();

    void bind(TransactionsVO transactions);
}
