package com.chandilsachin.personal_finance.database

/**
 * Created by sachin on 6/6/17.
 */
open class Constants{
    companion object{

        // Tables
        const val TABLE_EXPENSES = "expenses"
        const val TABLE_EXPENSES_SYNC = "expenses_sync"

        // Columns
        const val SPEND_ID = "spend_id"
        const val REMARK = "remark"
        const val AMOUNT = "amount"
        const val TIMESTAMP = "timestamp"
        const val SPEND = "spend"


        const val SYNC = "sync"
    }
}