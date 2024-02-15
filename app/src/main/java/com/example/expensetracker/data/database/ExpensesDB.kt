package com.example.expensetracker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.expensetracker.data.converters.Converters
import com.example.expensetracker.data.models.Expenses.ExpenseCategory
import com.example.expensetracker.data.models.Expenses.ExpenseItem
import com.example.expensetracker.data.models.idea.Idea
import com.example.expensetracker.data.models.currency.Currency

@Database(
    entities = [ExpenseItem::class, ExpenseCategory::class, Idea::class, Currency::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ExpensesDB : RoomDatabase() {
    abstract val expenseItemsDao: ExpenseItemsDAO
    abstract val categoryDao: ExpenseCategoryDao
    abstract val currencyDao : CurrencyDao
    abstract val ideaDao : IdeaDao

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
            ).createFromAsset("database/currency.db").build()
        }
    }
}

