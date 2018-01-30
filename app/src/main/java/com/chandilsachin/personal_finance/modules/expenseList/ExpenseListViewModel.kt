package com.chandilsachin.personal_finance.modules.expenseList

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.chandilsachin.personal_finance.dagger.MyApplication
import com.chandilsachin.personal_finance.database.LocalRepo
import com.chandilsachin.personal_finance.database.entities.Expense
import io.reactivex.android.schedulers.AndroidSchedulers
import lecho.lib.hellocharts.model.AxisValue
import lecho.lib.hellocharts.model.Column
import lecho.lib.hellocharts.model.SubcolumnValue
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * Created by sachin on 30/12/17.
 */

class ExpenseListViewModel : ViewModel() {

    @Inject
    lateinit var localRepo: LocalRepo

    val expenseListLiveData = MutableLiveData<ArrayList<Expense>>()
    val expenseListPagingLiveData = MutableLiveData<ArrayList<Expense>>()
    val graphDataLiveData = MutableLiveData<List<Column>>()
    var axisXValue = ArrayList<AxisValue>()

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

    fun generateMonthlyGraph() {
        axisXValue = ArrayList<AxisValue>()
        var index = 0
        localRepo.getLast12MonthExpense()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ l ->
                    var list = l.map { it ->
                        val c = Calendar.getInstance()
                        c.set(Calendar.MONTH, it.month)
                        axisXValue.add(AxisValue(index++.toFloat()).setLabel(c.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())))
                        SubcolumnValue(it.totalSpend)
                    }
                    var colList = list.map {
                        Column(listOf(it))
                    }
                    graphDataLiveData.value  = colList
                }, {it.printStackTrace()})

    }


}
