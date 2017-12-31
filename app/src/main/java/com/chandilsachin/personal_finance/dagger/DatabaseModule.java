package com.chandilsachin.personal_finance.dagger;

import android.content.Context;

import com.chandilsachin.personal_finance.database.ExpenseDao;
import com.chandilsachin.personal_finance.database.ExpenseDatabase;
import com.chandilsachin.personal_finance.database.LocalRepo;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    public ExpenseDao getDatabaseObject(Context context) {
        return ExpenseDatabase.Companion.getInstance(context);
    }

    @Provides
    public LocalRepo getLocalRepo(){
        return new LocalRepo();
    }
}
