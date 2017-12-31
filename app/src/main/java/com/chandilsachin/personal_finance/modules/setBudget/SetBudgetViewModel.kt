package com.chandilsachin.personal_finance.modules.setBudget

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.preference.PreferenceManager
import com.chandilsachin.personal_finance.dagger.MyApplication
import com.chandilsachin.personal_finance.database.Preferences
import javax.inject.Inject

/**
 * Created by sachin on 31/12/17.
 */
class SetBudgetViewModel: ViewModel(){

    @Inject
    lateinit var preferences: Preferences

    var budgetLiveData = MutableLiveData<Float>()
    var recurrenceBudgetLiveData = MutableLiveData<Boolean>()

    init {
        MyApplication.component.inject(this)
    }

    fun setBudget(budget: Float){
        preferences.setBudget(budget)
    }

    fun getBudget(){
        budgetLiveData.value = preferences.getBudget()
    }

    fun setRecurrenceBudget(recurrenceBudget: Boolean){
        preferences.setRecurrence(recurrenceBudget)
    }

    fun getRecurrenceBudget(){
        recurrenceBudgetLiveData.value = preferences.isRecurrence()
    }
}