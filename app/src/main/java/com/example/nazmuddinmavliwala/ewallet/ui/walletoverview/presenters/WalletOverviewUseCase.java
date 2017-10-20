package com.example.nazmuddinmavliwala.ewallet.ui.walletoverview.presenters;

import com.example.nazmuddinmavliwala.ewallet.base.presenters.UseCase;
import com.example.nazmuddinmavliwala.ewallet.domain.executers.ExecutionThread;
import com.example.nazmuddinmavliwala.ewallet.domain.executers.PostExecutionThread;

import javax.inject.Inject;

/**
 * Created by nazmuddinmavliwala on 20/10/2017.
 */

public class WalletOverviewUseCase extends UseCase<WalletOverviewRepository> {

    @Inject
    public WalletOverviewUseCase(ExecutionThread executionThread,
                                 PostExecutionThread postExecutionThread,
                                 WalletOverviewRepository repository) {
        super(executionThread, postExecutionThread, repository);
    }
}
