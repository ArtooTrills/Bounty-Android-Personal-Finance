package com.chandilsachin.personal_finance.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.chandilsachin.personal_finance.database.Constants
import java.util.*

/**
 * Created by sachin on 22/5/17.
 */
@Entity(tableName = Constants.TABLE_EXPENSES)
class Expense(@ColumnInfo(name = Constants.REMARK) var remark: String = "",
              @ColumnInfo(name = Constants.AMOUNT) var amount: Float = 0f,
              @ColumnInfo(name = Constants.SPEND) var spend: Boolean = true,
              @ColumnInfo(name = Constants.TIMESTAMP) var timestamp: Date = Date()) {

    @ColumnInfo(name = Constants.SPEND_ID)
    @PrimaryKey(autoGenerate = true)
    var spendId: Long = 0
}
