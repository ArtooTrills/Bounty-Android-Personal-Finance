package com.chandilsachin.personal_finance.database

import android.arch.persistence.room.*
import com.chandilsachin.personal_finance.database.entities.Expense
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

    @Query("SELECT * FROM ${Constants.TABLE_EXPENSES} ORDER BY ${Constants.TIMESTAMP} LIMIT 20 OFFSET :arg0")
    fun queryAll(fetchedCount: Int):Single<List<Expense>>

    @Query("SELECT * FROM ${Constants.TABLE_EXPENSES} WHERE ${Constants.SPEND_ID}=:arg0")
    fun query(expenseId: Long):Single<Expense>
}