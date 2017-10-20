package com.example.nazmuddinmavliwala.ewallet.ui.overview.presenters;

import com.example.nazmuddinmavliwala.ewallet.base.presenters.UseCase;
import com.example.nazmuddinmavliwala.ewallet.domain.executers.ExecutionThread;
import com.example.nazmuddinmavliwala.ewallet.domain.executers.PostExecutionThread;

import javax.inject.Inject;

/**
 * Created by nazmuddinmavliwala on 20/10/2017.
 */

public class OverviewUsecase extends UseCase<OverviewRepository> {

    @Inject
    public OverviewUsecase(ExecutionThread executionThread,
                           PostExecutionThread postExecutionThread,
                           OverviewRepository repository) {
        super(executionThread, postExecutionThread, repository);
    }
}
