package com.savenko.track.data.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.savenko.track.data.database.currenciesRelated.CurrenciesPreferenceDao
import com.savenko.track.data.database.currenciesRelated.CurrencyDao
import com.savenko.track.data.database.expensesRelated.ExpenseCategoryDao
import com.savenko.track.data.database.expensesRelated.ExpenseItemsDAO
import com.savenko.track.data.database.ideaRelated.ExpenseLimitsDao
import com.savenko.track.data.database.ideaRelated.IncomePlansDao
import com.savenko.track.data.database.ideaRelated.SavingsDao
import com.savenko.track.data.database.incomeRelated.IncomeCategoryDao
import com.savenko.track.data.database.incomeRelated.IncomeDao
import com.savenko.track.data.other.constants.DB_PATH
import com.savenko.track.data.other.converters.roomConverters.Converters
import com.savenko.track.domain.models.currency.CurrenciesPreference
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.expenses.ExpenseItem
import com.savenko.track.domain.models.idea.ExpenseLimits
import com.savenko.track.domain.models.idea.IncomePlans
import com.savenko.track.domain.models.idea.Savings
import com.savenko.track.domain.models.incomes.IncomeCategory
import com.savenko.track.domain.models.incomes.IncomeItem

@Database(
    entities = [ExpenseItem::class, ExpenseCategory::class, Currency::class, CurrenciesPreference::class, IncomeItem::class, IncomeCategory::class, Savings::class,  IncomePlans::class, ExpenseLimits::class],
    version = 2
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
            ).addMigrations(MIGRATION_1_2).createFromAsset(DB_PATH).build()
        }
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS currency (
                        ticker TEXT PRIMARY KEY NOT NULL,
                        name TEXT NOT NULL,
                        type TEXT NOT NULL,
                        rate REAL
                    )
                """.trimIndent()
                )
                database.execSQL("DELETE FROM currency")
                val insertStatement = """
                    INSERT INTO currency (ticker, name, type, rate) VALUES
                    ('AED', 'United Arab Emirates Dirham', 'default', NULL),
                    ('ARS', 'Argentine Peso', 'default', NULL),
                    ('AUD', 'Australian Dollar', 'default', NULL),
                    ('BRL', 'Brazilian Real', 'default', NULL),
                    ('CAD', 'Canadian Dollar', 'default', NULL),
                    ('CHF', 'Swiss Franc', 'default', NULL),
                    ('CLP', 'Chilean Peso', 'default', NULL),
                    ('CNY', 'Chinese Yuan', 'default', NULL),
                    ('COP', 'Colombian Peso', 'default', NULL),
                    ('CZK', 'Czech Koruna', 'default', NULL),
                    ('DKK', 'Danish Krone', 'default', NULL),
                    ('EGP', 'Egyptian Pound', 'default', NULL),
                    ('EUR', 'Euro', 'default', NULL),
                    ('GBP', 'British Pound Sterling', 'default', NULL),
                    ('HKD', 'Hong Kong Dollar', 'default', NULL),
                    ('HUF', 'Hungarian Forint', 'default', NULL),
                    ('IDR', 'Indonesian Rupiah', 'default', NULL),
                    ('ILS', 'Israeli New Shekel', 'default', NULL),
                    ('INR', 'Indian Rupee', 'default', NULL),
                    ('JPY', 'Japanese Yen', 'default', NULL),
                    ('KRW', 'South Korean Won', 'default', NULL),
                    ('MXN', 'Mexican Peso', 'default', NULL),
                    ('MYR', 'Malaysian Ringgit', 'default', NULL),
                    ('NGN', 'Nigerian Naira', 'default', NULL),
                    ('NOK', 'Norwegian Krone', 'default', NULL),
                    ('NZD', 'New Zealand Dollar', 'default', NULL),
                    ('PEN', 'Peruvian Sol', 'default', NULL),
                    ('PHP', 'Philippine Peso', 'default', NULL),
                    ('PKR', 'Pakistani Rupee', 'default', NULL),
                    ('PLN', 'Polish Zloty', 'default', NULL),
                    ('RUB', 'Russian Ruble', 'default', NULL),
                    ('SAR', 'Saudi Riyal', 'default', NULL),
                    ('SEK', 'Swedish Krona', 'default', NULL),
                    ('SGD', 'Singapore Dollar', 'default', NULL),
                    ('THB', 'Thai Baht', 'default', NULL),
                    ('TRY', 'Turkish Lira', 'default', NULL),
                    ('TWD', 'New Taiwan Dollar', 'default', NULL),
                    ('UAH', 'Ukrainian Hryvnia', 'default', NULL),
                    ('USD', 'United States Dollar', 'default', NULL),
                    ('ZAR', 'South African Rand', 'default', NULL),
                    ('ADA', 'Cardano', 'crypto', NULL),
                    ('BCH', 'Bitcoin Cash', 'crypto', NULL),
                    ('BNB', 'Binance Coin', 'crypto', NULL),
                    ('BTC', 'Bitcoin', 'crypto', NULL),
                    ('BUSD', 'Binance USD', 'crypto', NULL),
                    ('DOGE', 'Dogecoin', 'crypto', NULL),
                    ('DOT', 'Polkadot', 'crypto', NULL),
                    ('ETH', 'Ethereum', 'crypto', NULL),
                    ('LTC', 'Litecoin', 'crypto', NULL),
                    ('SHIB', 'Shiba Inu', 'crypto', NULL),
                    ('SOL', 'Solana', 'crypto', NULL),
                    ('TRX', 'TRON', 'crypto', NULL),
                    ('USDC', 'USD Coin', 'crypto', NULL),
                    ('USDT', 'Tether', 'crypto', NULL),
                    ('XRP', 'Ripple', 'crypto', NULL),
                    ('XAU', 'Gold', 'other', NULL),
                    ('XAG', 'Silver', 'other', NULL);
                """.trimIndent()
                database.execSQL(insertStatement)
            }
        }
    }
}

