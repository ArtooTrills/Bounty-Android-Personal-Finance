package com.chandilsachin.personal_finance.modules.setBudget


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.chandilsachin.personal_finance.R
import com.chandilsachin.personal_finance.util.initViewModel
import com.chandilsachin.personal_finance.util.lifecycle.arch.LifeCycleFragment
import kotlinx.android.synthetic.main.fragment_add_budget.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class SetBudgetFragment : LifeCycleFragment() {

    val viewModel: SetBudgetViewModel by lazy {
        initViewModel(SetBudgetViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_add_budget
    }

    override fun init(v: View?, savedInstanceState: Bundle?) {

        setUpToolbar(main_toolbar, R.string.setBudget)
        setDisplayHomeAsUpEnabled(true)
    }

    override fun initLoadViews() {

        viewModel.budgetLiveData.observe(this, Observer { etBudgetAmount.setText(it.toString()) })
        viewModel.recurrenceBudgetLiveData.observe(this, Observer { it?.let { cbRecurrenceBudget.isChecked = it  }})

        viewModel.getBudget()
        viewModel.getRecurrenceBudget()
    }

    override fun setUpEvents() {

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menuAdd -> {
                viewModel.setBudget(etBudgetAmount.text.toString().toFloat())
                viewModel.setRecurrenceBudget(cbRecurrenceBudget.isChecked)
                activity?.onBackPressed()
                return true;
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        fun newInstance(): SetBudgetFragment {
            val fragment = SetBudgetFragment()
            return fragment
        }

    }

}// Required empty public constructor
