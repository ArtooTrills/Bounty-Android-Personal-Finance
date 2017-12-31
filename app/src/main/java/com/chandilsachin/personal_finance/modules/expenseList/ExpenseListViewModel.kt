package com.chandilsachin.personal_finance.modules.expenseList

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.chandilsachin.personal_finance.dagger.MyApplication
import com.chandilsachin.personal_finance.database.LocalRepo
import com.chandilsachin.personal_finance.database.entities.Expense
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * Created by sachin on 30/12/17.
 */

class ExpenseListViewModel : ViewModel() {

    @Inject
    lateinit var localRepo: LocalRepo

    val expenseListLiveData = MutableLiveData<ArrayList<Expense>>()
    val expenseListPagingLiveData = MutableLiveData<ArrayList<Expense>>()
    init {
        MyApplication.component.inject(this)
    }

    fun getExpenseList() {
        localRepo.getAllExpense(0)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list -> expenseListLiveData.value = list },
                        { it.printStackTrace() })
    }

    fun loadMoreExpenseItems(itemsLoaded: Int) {
        localRepo.getAllExpense(itemsLoaded)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list -> expenseListPagingLiveData.value = list },
                        { it.printStackTrace() })
    }

}
