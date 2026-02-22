package com.savenko.track.shared.data.database.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.savenko.track.shared.data.database.currencies.CurrenciesPreferenceDao
import com.savenko.track.shared.data.database.currencies.CurrencyDao
import com.savenko.track.shared.data.database.expensesRelated.ExpenseCategoryDao
import com.savenko.track.shared.data.database.expensesRelated.ExpenseItemsDAO
import com.savenko.track.shared.data.database.ideas.ExpenseLimitsDao
import com.savenko.track.shared.data.database.ideas.IncomePlansDao
import com.savenko.track.shared.data.database.ideas.SavingsDao
import com.savenko.track.shared.data.database.incomeRelated.IncomeCategoryDao
import com.savenko.track.shared.data.database.incomeRelated.IncomeDao
import com.savenko.track.shared.data.other.converters.roomConverters.Converters
import com.savenko.track.shared.domain.models.currency.CurrenciesPreference
import com.savenko.track.shared.domain.models.currency.Currency
import com.savenko.track.shared.domain.models.expenses.ExpenseCategory
import com.savenko.track.shared.domain.models.expenses.ExpenseItem
import com.savenko.track.shared.domain.models.idea.ExpenseLimits
import com.savenko.track.shared.domain.models.idea.IncomePlans
import com.savenko.track.shared.domain.models.idea.Savings
import com.savenko.track.shared.domain.models.incomes.IncomeCategory
import com.savenko.track.shared.domain.models.incomes.IncomeItem

const val DB_NAME = "main.db"

@ConstructedBy(AppDatabaseConstructor::class)
@Database(
    entities = [
        ExpenseItem::class,
        ExpenseCategory::class,
        Currency::class,
        CurrenciesPreference::class,
        IncomeItem::class,
        IncomeCategory::class,
        Savings::class,
        IncomePlans::class,
        ExpenseLimits::class,
    ],
    version = 2,
    exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class ExpensesDB : RoomDatabase() {
    abstract val expenseItemsDao: ExpenseItemsDAO
    abstract val categoryDao: ExpenseCategoryDao
    abstract val currencyDao: CurrencyDao
    abstract val currenciesPreferenceDao: CurrenciesPreferenceDao
    abstract val incomeDao: IncomeDao
    abstract val incomeCategoryDao: IncomeCategoryDao
    abstract val expenseLimitsDao: ExpenseLimitsDao
    abstract val incomePlansDao: IncomePlansDao
    abstract val savingsDao: SavingsDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(connection: SQLiteConnection) {
                connection.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS currency (
                        ticker TEXT PRIMARY KEY NOT NULL,
                        name TEXT NOT NULL,
                        type TEXT NOT NULL,
                        rate REAL
                    )
                """.trimIndent()
                )
                connection.execSQL("DELETE FROM currency")
                val insertStatement = """
                    INSERT INTO currency (ticker, name, type, rate) VALUES
                    ('AED', 'UAE Dirham', 'default', NULL),
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
                connection.execSQL(insertStatement)
            }
        }
    }
}
