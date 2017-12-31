package com.chandilsachin.personal_finance.database

import android.arch.persistence.room.TypeConverter
import java.util.Date


/**
 * Created by sachin on 24/5/17.
 */
class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        val cal = Date(value)
        return cal
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }

}