package com.example.expensetracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.expensetracker.domain.ExpenseItem

@Database(
    entities = [ExpenseItem::class],
     version = 1
)
abstract class ExpensesDB : RoomDatabase() {
abstract val dao : ExpensesDAO
    companion object {
        private var INSTANCE: ExpensesDB? = null

        fun getInstance(context: Context): ExpensesDB {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): ExpensesDB {
            return Room.databaseBuilder(
                context.applicationContext,
                ExpensesDB::class.java, "main.db"
            ).build()
        }
    }
}