package com.chandilsachin.personal_finance.modules.addExpense

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.chandilsachin.personal_finance.dagger.MyApplication
import com.chandilsachin.personal_finance.database.LocalRepo
import com.chandilsachin.personal_finance.database.entities.Expense
import com.chandilsachin.personal_finance.eventBus.UpdateExpenseEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

/**
 * Created by sachin on 30/12/17.
 */

class AddExpenseViewModel: ViewModel() {

    @Inject
    lateinit var localRepo: LocalRepo

    var expenseDetailsLiveData = MutableLiveData<Expense>()

    init {
        MyApplication.component.inject(this)
    }

    fun addExpense(expense: Expense) {
        localRepo.addExpense(expense)
                .subscribe()
    }

    fun updateExpense(expense: Expense) {
        localRepo.updateExpense(expense)
                .subscribe({ _ ->
                    //EventBus.getDefault().post(UpdateExpenseEvent())
                })
    }

    fun getExpenseDetails(expenseId: Long) {
        localRepo.getExpenseDetails(expenseId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ expense -> expenseDetailsLiveData.value = expense },
                        { it.printStackTrace() })
    }

}
