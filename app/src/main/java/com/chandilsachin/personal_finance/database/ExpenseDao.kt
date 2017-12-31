package com.chandilsachin.personal_finance.database

import android.arch.persistence.room.*
import com.chandilsachin.personal_finance.database.entities.Expense
import com.chandilsachin.personal_finance.database.entities.MonthSpendData
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by sachin on 22/5/17.
 */
@Dao
interface ExpenseDao {

    @Insert
    fun insert(expense: Expense): Long

    @Insert
    fun insert(expenses: List<Expense>): List<Long>

    @Update
    fun update(expense: Expense): Int

    @Delete
    fun delete(expense: Expense): Int

    @Query("SELECT * FROM ${Constants.TABLE_EXPENSES} ORDER BY ${Constants.TIMESTAMP} DESC")
    fun queryAll():Flowable<List<Expense>>

    @Query("SELECT DISTINCT SUM(${Constants.AMOUNT}) as ${Constants.TOTAL_SPEND}, strftime('%m', ${Constants.TIMESTAMP}) as ${Constants.MONTH} FROM ${Constants.TABLE_EXPENSES} GROUP BY strftime('%m', ${Constants.TIMESTAMP}) ORDER BY strftime('%m', ${Constants.TIMESTAMP}) DESC limit 5")
    fun queryLast12MonthExpense():Flowable<List<MonthSpendData>>

    @Query("SELECT * FROM ${Constants.TABLE_EXPENSES} WHERE ${Constants.SPEND_ID}=:arg0")
    fun query(expenseId: Long):Single<Expense>
}