package com.chandilsachin.personal_finance.modules.expenseList

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chandilsachin.personal_finance.database.entities.Expense

/**
 * Created by sachin on 31/12/17.
 */

class ExpenseListAdapter(context: Context, var list: List<Expense>) : RecyclerView.Adapter<ExpenseListAdapter.ViewHolder>() {

    private var layoutInflater: LayoutInflater? = null

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }
}
