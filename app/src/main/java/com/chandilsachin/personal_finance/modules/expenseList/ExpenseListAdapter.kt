package com.chandilsachin.personal_finance.modules.expenseList

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chandilsachin.personal_finance.R
import com.chandilsachin.personal_finance.database.Date
import com.chandilsachin.personal_finance.database.entities.Expense
import kotlinx.android.synthetic.main.list_item_expense_list.view.*

/**
 * Created by sachin on 31/12/17.
 */

class ExpenseListAdapter(context: Context?, var list: ArrayList<Expense>) : RecyclerView.Adapter<ExpenseListAdapter.ViewHolder>() {

    private var layoutInflater: LayoutInflater? = null
    var onItemClickListener: (expense: Expense) -> Unit = {}

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val item = getItem(position)
        holder?.bind(item)
        holder?.itemView?.setOnClickListener{
            onItemClickListener(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(layoutInflater?.inflate(R.layout.list_item_expense_list, parent, false))
    }

    fun getItem(position: Int) = list[position]

    fun addItems(items: ArrayList<Expense>){
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun setItems(items: ArrayList<Expense>){
        list = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView){
        @SuppressLint("SetTextI18n")
        fun bind(item: Expense){
            itemView.tvFace.text = item.remark[0].toString()
            itemView.tvRemarks.text = item.remark
            itemView.tvTimestamp.text = Date(item.timestamp).getPrettyDate(itemView.tvTimestamp.context)
            itemView.tvAmount.text = "\u20B9 ${item.amount}"
            itemView.ivPocketSpend.setImageResource(if (item.spend) R.mipmap.ic_wallet else R.mipmap.ic_wallet_disabled)
        }
    }
}
