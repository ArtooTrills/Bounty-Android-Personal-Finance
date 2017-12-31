package com.chandilsachin.personal_finance.database.entities

import android.arch.persistence.room.ColumnInfo
import com.chandilsachin.personal_finance.database.Constants

/**
 * Created by sachin on 31/12/17.
 */
class MonthSpendData(@ColumnInfo(name = Constants.TOTAL_SPEND) var totalSpend: Float = 0f,
                     @ColumnInfo(name = Constants.MONTH) var month: Int = -1) {

}