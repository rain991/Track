package com.example.track.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.track.data.constants.DB_PATH
import com.example.track.data.converters.Converters
import com.example.track.data.database.currenciesRelated.CurrenciesPreferenceDao
import com.example.track.data.database.currenciesRelated.CurrencyDao
import com.example.track.data.database.expensesRelated.ExpenseCategoryDao
import com.example.track.data.database.expensesRelated.ExpenseItemsDAO
import com.example.track.data.database.ideaRelated.IdeaDao
import com.example.track.data.database.incomeRelated.IncomeCategoryDao
import com.example.track.data.database.incomeRelated.IncomeDao
import com.example.track.data.models.Expenses.ExpenseCategory
import com.example.track.data.models.Expenses.ExpenseItem
import com.example.track.data.models.currency.CurrenciesPreference
import com.example.track.data.models.currency.Currency
import com.example.track.data.models.idea.Idea
import com.example.track.data.models.incomes.IncomeCategory
import com.example.track.data.models.incomes.IncomeItem

@Database(
    entities = [ExpenseItem::class, ExpenseCategory::class, Idea::class, Currency::class, CurrenciesPreference::class, IncomeItem::class, IncomeCategory::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ExpensesDB : RoomDatabase() {
    abstract val expenseItemsDao: ExpenseItemsDAO
    abstract val categoryDao: ExpenseCategoryDao
    abstract val currencyDao: CurrencyDao
    abstract val ideaDao: IdeaDao
    abstract val currenciesPreferenceDao: CurrenciesPreferenceDao
    abstract val incomeDao : IncomeDao
    abstract val incomeCategoryDao: IncomeCategoryDao

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

