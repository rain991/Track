package com.savenko.track.data.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.savenko.track.data.other.constants.DB_PATH
import com.savenko.track.data.other.converters.roomConverters.Converters
import com.savenko.track.data.database.currenciesRelated.CurrenciesPreferenceDao
import com.savenko.track.data.database.currenciesRelated.CurrencyDao
import com.savenko.track.data.database.expensesRelated.ExpenseCategoryDao
import com.savenko.track.data.database.expensesRelated.ExpenseItemsDAO
import com.savenko.track.data.database.ideaRelated.ExpenseLimitsDao
import com.savenko.track.data.database.ideaRelated.IncomePlansDao
import com.savenko.track.data.database.ideaRelated.SavingsDao
import com.savenko.track.data.database.incomeRelated.IncomeCategoryDao
import com.savenko.track.data.database.incomeRelated.IncomeDao
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.expenses.ExpenseItem
import com.savenko.track.domain.models.currency.CurrenciesPreference
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.domain.models.idea.ExpenseLimits
import com.savenko.track.domain.models.idea.IncomePlans
import com.savenko.track.domain.models.idea.Savings
import com.savenko.track.domain.models.incomes.IncomeCategory
import com.savenko.track.domain.models.incomes.IncomeItem

@Database(
    entities = [ExpenseItem::class, ExpenseCategory::class, Currency::class, CurrenciesPreference::class, IncomeItem::class, IncomeCategory::class, Savings::class,  IncomePlans::class, ExpenseLimits::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ExpensesDB : RoomDatabase() {
    abstract val expenseItemsDao: ExpenseItemsDAO
    abstract val categoryDao: ExpenseCategoryDao
    abstract val currencyDao: CurrencyDao
    abstract val currenciesPreferenceDao: CurrenciesPreferenceDao
    abstract val incomeDao : IncomeDao
    abstract val incomeCategoryDao: IncomeCategoryDao
    abstract val expenseLimitsDao : ExpenseLimitsDao
    abstract val incomePlansDao : IncomePlansDao
    abstract val savingsDao : SavingsDao
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

