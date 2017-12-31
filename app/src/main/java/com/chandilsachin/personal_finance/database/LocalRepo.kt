package com.chandilsachin.personal_finance.database

import com.chandilsachin.personal_finance.dagger.MyApplication
import com.chandilsachin.personal_finance.database.entities.Expense
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by sachin on 30/12/17.
 */

class LocalRepo {

    @Inject
    lateinit var dao: ExpenseDao

    init {
        MyApplication.component.inject(this)
    }

    fun addExpense(expense: Expense): Single<Boolean> {
        return Single.create <Boolean>{
            it.onSuccess(dao.insert(expense) != -1L)
        }.subscribeOn(Schedulers.computation())
    }

    fun updateExpense(expense: Expense): Single<Boolean> {
        return Single.create<Boolean> {
            it.onSuccess(dao.update(expense) != -1)
        }.subscribeOn(Schedulers.computation())
    }

    fun deleteExpense(expense: Expense): Single<Boolean> {
        return Single.create <Boolean>{
            it.onSuccess(dao.delete(expense) > 0)
        }.subscribeOn(Schedulers.computation())
    }

    fun getAllExpense(fetchedCount: Int): Flowable<ArrayList<Expense>> {
        return dao.queryAll(fetchedCount).subscribeOn(Schedulers.computation()).map { ArrayList(it) }
    }

    fun getExpenseDetails(expenseId: Long): Single<Expense> {
        return dao.query(expenseId).subscribeOn(Schedulers.computation())
    }
}
