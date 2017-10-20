package com.example.nazmuddinmavliwala.ewallet.ui.addexpensemoney.presenters;

import com.example.nazmuddinmavliwala.ewallet.base.presenters.UseCase;
import com.example.nazmuddinmavliwala.ewallet.domain.executers.ExecutionThread;
import com.example.nazmuddinmavliwala.ewallet.domain.executers.PostExecutionThread;

import javax.inject.Inject;

/**
 * Created by nazmuddinmavliwala on 20/10/2017.
 */

public class AddExpenseMoneyUseCase extends UseCase<AddExpenseMoneyRepository> {

    @Inject
    public AddExpenseMoneyUseCase(ExecutionThread executionThread,
                                  PostExecutionThread postExecutionThread,
                                  AddExpenseMoneyRepository repository) {
        super(executionThread, postExecutionThread, repository);
    }
}
