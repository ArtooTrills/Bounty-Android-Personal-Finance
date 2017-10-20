package com.example.nazmuddinmavliwala.ewallet.ui.reports.presenters;

import com.example.nazmuddinmavliwala.ewallet.base.presenters.UseCase;
import com.example.nazmuddinmavliwala.ewallet.domain.executers.ExecutionThread;
import com.example.nazmuddinmavliwala.ewallet.domain.executers.PostExecutionThread;

import javax.inject.Inject;

/**
 * Created by nazmuddinmavliwala on 20/10/2017.
 */

public class ReportsUseCase extends UseCase<ReportsRepository> {

    @Inject
    public ReportsUseCase(ExecutionThread executionThread,
                          PostExecutionThread postExecutionThread,
                          ReportsRepository repository) {
        super(executionThread, postExecutionThread, repository);
    }
}
