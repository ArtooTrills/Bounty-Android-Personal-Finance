package com.chandilsachin.personal_finance

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.chandilsachin.personal_finance.database.Preferences
import com.chandilsachin.personal_finance.util.loadFragment
import com.chandilsachin.personal_finance.modules.expenseList.ExpenseListFragment
import com.chandilsachin.personal_finance.modules.readMessage.ReadMessageFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val preferences = Preferences(this)

        if(preferences.isAppOpeningFirstTime()) {
            loadFragment(R.id.frameLayoutFragmentContainer,
                    ReadMessageFragment.newInstance("", ""), this, false)
        }
        else
            loadFragment(R.id.frameLayoutFragmentContainer,
                ExpenseListFragment.newInstance(), this)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1)
            super.onBackPressed()
        else
            finish()
    }
}
