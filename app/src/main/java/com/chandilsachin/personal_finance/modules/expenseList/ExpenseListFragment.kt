package com.chandilsachin.personal_finance.modules.expenseList


import android.arch.lifecycle.Observer
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.LinearLayoutManager
import android.text.format.DateUtils
import android.view.View
import com.chandilsachin.personal_finance.R
import com.chandilsachin.personal_finance.modules.addExpense.AddExpenseFragment
import com.chandilsachin.personal_finance.modules.setBudget.SetBudgetFragment
import com.chandilsachin.personal_finance.util.initViewModel
import com.chandilsachin.personal_finance.util.lifecycle.arch.LifeCycleFragment
import com.chandilsachin.personal_finance.util.loadFragmentSlideUp
import kotlinx.android.synthetic.main.fragment_expense_list.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import lecho.lib.hellocharts.model.*
import java.util.*
import kotlin.collections.ArrayList
import lecho.lib.hellocharts.view.LineChartView


class ExpenseListFragment : LifeCycleFragment() {

    val viewModel: ExpenseListViewModel by lazy {
        initViewModel(ExpenseListViewModel::class.java)
    }

    var linearLayoutManager: LinearLayoutManager? = null
    var expenseListAdapter: ExpenseListAdapter? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_expense_list
    }

    override fun init(v: View?, savedInstanceState: Bundle?) {

        setUpToolbar(main_toolbar, DateUtils.formatDateTime(context, Date().time, DateUtils.FORMAT_SHOW_DATE or
                DateUtils.FORMAT_SHOW_YEAR or DateUtils.FORMAT_SHOW_WEEKDAY or DateUtils.FORMAT_ABBREV_ALL))

        main_collapsing.setExpandedTitleColor(Color.TRANSPARENT)

        linearLayoutManager = LinearLayoutManager(context)
        rvExpenseLise.layoutManager = linearLayoutManager

        expenseListAdapter = ExpenseListAdapter(context, ArrayList())
        rvExpenseLise.adapter = expenseListAdapter

    }

    override fun initLoadViews() {
        viewModel.expenseListLiveData.observe(this, Observer {
            it?.let {
                expenseListAdapter?.setItems(it)
            }
        })

        viewModel.expenseListPagingLiveData.observe(this, Observer {
            it?.let {
                expenseListAdapter?.addItems(it)
            }
        })

        viewModel.getExpenseList()

        graphExpense.isInteractive = false
        viewModel.graphDataLiveData.observe(this, Observer {

            val chartData = ColumnChartData(it)
            chartData.axisXBottom = Axis(viewModel.axisXValue)
            //chartData.axisYLeft = Axis().setMaxLabelChars(2)
            graphExpense.columnChartData = chartData

        })

        viewModel.generateMonthlyGraph()
    }

    /*@Subscribe
    fun expenseUpdated(event: UpdateExpenseEvent){
        viewModel.getExpenseList()
    }*/


    override fun setUpEvents() {
        appBarLayoutExpenseList.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            internal var isShow = false
            internal var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        main_collapsing.setExpandedTitleColor(resources.getColor(R.color.colorPrimaryText, resources.newTheme()))
                    } else {
                        main_collapsing.setExpandedTitleColor(resources.getColor(R.color.colorPrimaryText))
                    }
                    isShow = true
                } else if (isShow) {
                    main_collapsing.setExpandedTitleColor(Color.TRANSPARENT)
                    isShow = false
                }
            }
        })

        expenseListAdapter?.onItemClickListener = {
            loadFragmentSlideUp(R.id.frameLayoutFragmentContainer,
                    AddExpenseFragment.newInstanceToEdit(it.spendId))
        }

        btnAddBudget.setOnClickListener {
            loadFragmentSlideUp(R.id.frameLayoutFragmentContainer,
                    SetBudgetFragment.newInstance())
        }
        btnAddExpense.setOnClickListener {
            loadFragmentSlideUp(R.id.frameLayoutFragmentContainer,
                    AddExpenseFragment.newInstanceToAdd())
        }
    }

    override fun onStart() {
        super.onStart()
        //EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        //EventBus.getDefault().unregister(this)
    }

    companion object {

        fun newInstance(): ExpenseListFragment {
            val fragment = ExpenseListFragment()
            val args = Bundle()
            fragment.setArguments(args)
            return fragment
        }
    }

}// Required empty public constructor
