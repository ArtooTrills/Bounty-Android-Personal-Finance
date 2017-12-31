package com.chandilsachin.personal_finance.modules.addExpense


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import com.chandilsachin.personal_finance.R
import com.chandilsachin.personal_finance.database.Date
import com.chandilsachin.personal_finance.database.entities.Expense
import com.chandilsachin.personal_finance.util.initViewModel
import com.chandilsachin.personal_finance.util.lifecycle.arch.LifeCycleFragment
import kotlinx.android.synthetic.main.fragment_add_expense.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.util.*


class AddExpenseFragment : LifeCycleFragment() {

    private var mMode: Int = -1
    private var mExpenseId: Long = -1
    private var mCalendarTimestamp = Calendar.getInstance();

    val viewModel: AddExpenseViewModel by lazy {
        initViewModel(AddExpenseViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mMode = arguments!!.getInt(ARG_MODE)
            mExpenseId = arguments!!.getLong(ARG_EXPENSE_ID)
        }
        setHasOptionsMenu(true)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_add_expense
    }

    override fun init(v: View?, savedInstanceState: Bundle?) {

        setUpToolbar(main_toolbar, R.string.add_expense)
        setDisplayHomeAsUpEnabled(true)
    }

    override fun initLoadViews() {

        if (mMode == MODE_EDIT) {
            viewModel.expenseDetailsLiveData.observe(this, Observer {
                it?.let {
                    setExpendDetailsToUi(it)
                    mCalendarTimestamp.timeInMillis = it.timestamp.time
                }
            })

            viewModel.getExpenseDetails(mExpenseId)
        }else{
            tvExpenseTimestamp.text = Date(mCalendarTimestamp.timeInMillis).getPrettyDate(context)
        }
    }

    override fun setUpEvents() {
        tvExpenseTimestamp.setOnClickListener {
            showDatePicker()
        }
    }

    fun showDatePicker() {
        var datePickerDialog = DatePickerDialog(context,
                DatePickerDialog.OnDateSetListener { datePicker: DatePicker, y: Int, m: Int, d: Int ->
                    mCalendarTimestamp.set(y, m, d)
                    showTimePicker()
                }, mCalendarTimestamp.get(Calendar.YEAR), mCalendarTimestamp.get(Calendar.MONTH),
                mCalendarTimestamp.get(Calendar.DATE))
        datePickerDialog.show()
    }

    fun showTimePicker() {
        var timePickerDialog = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { timePicker, hour, minutes ->
            mCalendarTimestamp.set(Calendar.HOUR_OF_DAY, hour)
            mCalendarTimestamp.set(Calendar.MINUTE, minutes)
            var date = Date(mCalendarTimestamp)
            tvExpenseTimestamp.setText(date.getPrettyDate(context))
        }, mCalendarTimestamp.get(Calendar.HOUR_OF_DAY), mCalendarTimestamp.get(Calendar.MINUTE), false)
        timePickerDialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        if (mMode == MODE_EDIT)
            inflater?.inflate(R.menu.menu_edit, menu)
        else if (mMode == MODE_ADD)
            inflater?.inflate(R.menu.menu_add, menu)
        else
            super.onCreateOptionsMenu(menu, inflater)
    }

    private fun validateInputs(): Boolean{
        var res = true
        if(etSpendRemark.text.isEmpty()) {
            res = false
            showToast("Remark can't be empty!")
        }
        else if(etSpendAmount.text.isEmpty()) {
            res = false
            showToast("Amount can't be empty!")
        }
        return res
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menuEdit -> {
                if(!validateInputs()) return true
                val expense = getExpendDetailsFromUi()
                expense.spendId = mExpenseId
                viewModel.updateExpense(expense)
                activity?.onBackPressed()
                return true;
            }
            R.id.menuAdd -> {
                if(!validateInputs()) return true
                viewModel.addExpense(getExpendDetailsFromUi())
                activity?.onBackPressed()
                return true;
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun getExpendDetailsFromUi(): Expense {
        return Expense(etSpendRemark.text.toString(), etSpendAmount.text.toString().toFloat(),
                switchSpend.isChecked, mCalendarTimestamp.time)
    }

    fun setExpendDetailsToUi(expense: Expense) {
        etSpendRemark.setText(expense.remark)
        etSpendAmount.setText(expense.amount.toString())
        tvExpenseTimestamp.setText(Date(expense.timestamp.time).getPrettyDate(context))
        switchSpend.isChecked = expense.spend
    }

    companion object {
        private val ARG_MODE = "MODE"
        private val ARG_EXPENSE_ID = "EXPENSE_ID"

        val MODE_ADD = 1
        val MODE_EDIT = 2

        fun newInstanceToAdd(): AddExpenseFragment {
            val fragment = AddExpenseFragment()
            val args = Bundle()
            args.putInt(ARG_MODE, MODE_ADD)
            fragment.arguments = args
            return fragment
        }

        fun newInstanceToEdit(expenseId: Long): AddExpenseFragment {
            val fragment = AddExpenseFragment()
            val args = Bundle()
            args.putInt(ARG_MODE, MODE_EDIT)
            args.putLong(ARG_EXPENSE_ID, expenseId)
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
