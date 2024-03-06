package com.example.expensetracker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.expensetracker.data.constants.DB_PATH
import com.example.expensetracker.data.converters.Converters
import com.example.expensetracker.data.database.currenciesRelated.CurrenciesPreferenceDao
import com.example.expensetracker.data.database.currenciesRelated.CurrencyDao
import com.example.expensetracker.data.database.expensesRelated.ExpenseCategoryDao
import com.example.expensetracker.data.database.expensesRelated.ExpenseItemsDAO
import com.example.expensetracker.data.database.ideaRelated.IdeaDao
import com.example.expensetracker.data.models.Expenses.ExpenseCategory
import com.example.expensetracker.data.models.Expenses.ExpenseItem
import com.example.expensetracker.data.models.currency.CurrenciesPreference
import com.example.expensetracker.data.models.currency.Currency
import com.example.expensetracker.data.models.idea.Idea

@Database(
    entities = [ExpenseItem::class, ExpenseCategory::class, Idea::class, Currency::class, CurrenciesPreference::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ExpensesDB : RoomDatabase() {
    abstract val expenseItemsDao: ExpenseItemsDAO
    abstract val categoryDao: ExpenseCategoryDao
    abstract val currencyDao : CurrencyDao
    abstract val ideaDao : IdeaDao
    abstract val currenciesPreferenceDao : CurrenciesPreferenceDao

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
            ).createFromAsset(DB_PATH).build()
        }
    }
}

