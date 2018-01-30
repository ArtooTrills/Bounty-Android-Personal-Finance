package com.chandilsachin.personal_finance.database
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.chandilsachin.personal_finance.database.entities.Expense

/**
 * Created by sachin on 22/5/17.
 */

@Database(entities = arrayOf(Expense::class),
        version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ExpenseDatabase : RoomDatabase(){
    abstract fun foodDao(): ExpenseDao

    companion object{
        var TEST_MODE = false
        private val databaseName = "expense"

        private var db: ExpenseDatabase? = null
        private var dbInstance: ExpenseDao? = null
        fun getInstance(context:Context): ExpenseDao {
            if(dbInstance == null){
                if(TEST_MODE){
                    db = Room.inMemoryDatabaseBuilder(context, ExpenseDatabase::class.java).allowMainThreadQueries().build()
                    dbInstance = db?.foodDao()
                }

                else {
                    db = Room.databaseBuilder(context, ExpenseDatabase::class.java, databaseName).build()
                    dbInstance = db?.foodDao()
                }
            }
            return dbInstance!!;
        }

        private fun close(){
            db?.close()
        }
    }
}
