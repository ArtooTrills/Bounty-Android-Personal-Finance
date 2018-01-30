package com.chandilsachin.personal_finance.modules.readMessage


import android.Manifest
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import com.chandilsachin.personal_finance.R
import com.chandilsachin.personal_finance.database.Preferences
import com.chandilsachin.personal_finance.modules.expenseList.ExpenseListFragment
import com.chandilsachin.personal_finance.util.getAppCompactActivity
import com.chandilsachin.personal_finance.util.initViewModel
import com.chandilsachin.personal_finance.util.lifecycle.arch.LifeCycleFragment
import com.chandilsachin.personal_finance.util.loadFragment
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.fragment_read_message.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class ReadMessageFragment : LifeCycleFragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null

    val viewModel: ReadMessageViewModel by lazy {
        initViewModel(ReadMessageViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_read_message
    }

    override fun init(v: View?, savedInstanceState: Bundle?) {
        setUpToolbar(main_toolbar)
        progressLoadSpends.isIndeterminate = false
    }

    private fun askForMessageReadPermission() {

        val rxPermission = RxPermissions(activity!!)
        rxPermission.request(Manifest.permission.READ_SMS)
                .subscribe({granted ->
                    viewModel.loadSmsIntoDatabase(context!!)
                }, {

                })

    }

    override fun initLoadViews() {
        viewModel.smsFinishedReadingLiveData.observe(this, Observer {
            it?.let {
                progressLoadSpends.max = viewModel.totalSmsCount
                progressLoadSpends.progress = it
                if(it == viewModel.totalSmsCount){
                    btnSkipPermission.performClick()
                }
            }
        })
    }

    override fun setUpEvents() {

        btnSkipPermission.setOnClickListener {
            val preferences = Preferences(context!!)
            preferences.setOpenedFirstTime()

            loadFragment(R.id.frameLayoutFragmentContainer,
                    ExpenseListFragment.newInstance(), getAppCompactActivity())
        }

        btnGrantPermission.setOnClickListener {
            askForMessageReadPermission()
        }
    }


    companion object {
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): ReadMessageFragment {
            val fragment = ReadMessageFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
