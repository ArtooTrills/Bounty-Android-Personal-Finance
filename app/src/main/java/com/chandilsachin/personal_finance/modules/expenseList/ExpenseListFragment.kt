package com.chandilsachin.personal_finance.modules.expenseList


import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.LinearLayoutManager
import android.text.format.DateUtils
import android.view.View
import com.chandilsachin.personal_finance.R
import com.chandilsachin.personal_finance.modules.addExpense.AddExpenseFragment
import com.chandilsachin.personal_finance.util.lifecycle.arch.LifeCycleFragment
import com.chandilsachin.personal_finance.util.loadFragmentSlideUp
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import kotlinx.android.synthetic.main.fragment_expense_list.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.util.*


class ExpenseListFragment : LifeCycleFragment() {

    var linearLayoutManager: LinearLayoutManager? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_expense_list
    }

    override fun init(v: View?, savedInstanceState: Bundle?) {

        setUpToolbar(main_toolbar, DateUtils.formatDateTime(context, Date().time, 2))

        linearLayoutManager = LinearLayoutManager(context)
        rvExpenseLise.layoutManager = linearLayoutManager

        var series: BarGraphSeries<DataPoint> = BarGraphSeries(arrayOf(
                DataPoint(0.0, 1.0),
                DataPoint(1.0, 5.0),
                DataPoint(2.0, 3.0),
                DataPoint(3.0, 2.0),
                DataPoint(4.0, 6.0)
        ))
        series.isDrawValuesOnTop = true
        series.valuesOnTopColor = Color.BLACK
        series.spacing = 2
        series.dataWidth = 0.5
        series.isAnimated = true

        graphExpense.addSeries(series)
    }

    override fun initLoadViews() {

    }

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
                    }else{
                        main_collapsing.setExpandedTitleColor(resources.getColor(R.color.colorPrimaryText))
                    }
                    isShow = true
                } else if (isShow) {
                    main_collapsing.setExpandedTitleColor(Color.TRANSPARENT)
                    isShow = false
                }
            }
        })

        btnAddExpense.setOnClickListener {
            loadFragmentSlideUp(R.id.frameLayoutFragmentContainer,
                    AddExpenseFragment.newInstanceToEdit(1L))
        }
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
