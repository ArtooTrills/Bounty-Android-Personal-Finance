package com.example.nazmuddinmavliwala.ewallet.ui.expenseoverview.presenters;

import com.example.nazmuddinmavliwala.ewallet.base.presenters.UseCase;
import com.example.nazmuddinmavliwala.ewallet.domain.executers.ExecutionThread;
import com.example.nazmuddinmavliwala.ewallet.domain.executers.PostExecutionThread;

import javax.inject.Inject;

/**
 * Created by nazmuddinmavliwala on 20/10/2017.
 */

public class ExpenseOverviewUseCase extends UseCase<ExpenseOverviewRepository> {

    @Inject
    public ExpenseOverviewUseCase(ExecutionThread executionThread,
                                  PostExecutionThread postExecutionThread,
                                  ExpenseOverviewRepository repository) {
        super(executionThread, postExecutionThread, repository);
    }
}
