package com.savenko.track.shared.data.database.db

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask


fun getDatabaseBuilder(): RoomDatabase.Builder<ExpensesDB> {
    val dbFilePath = documentDirectory() + "/" + DB_NAME
    return Room.databaseBuilder<ExpensesDB>(
        name = dbFilePath,
    )
        .addCallback(
            object : RoomDatabase.Callback() {
                override fun onCreate(connection: SQLiteConnection) {
                    connection.execSQL(
                        """
                        INSERT OR IGNORE INTO currency (ticker, name, type, rate) VALUES
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
                    )

                    connection.execSQL(
                        """
                        INSERT OR IGNORE INTO income_categories (categoryId, note, colorId) VALUES
                        (1, 'Salary', '00FF00'),
                        (2, 'Freelance', 'FFA500'),
                        (3, 'Investment', 'FFD700'),
                        (4, 'Rental Income', '6495ED'),
                        (5, 'Business Income', 'FF4500'),
                        (6, 'Dividends', '8A2BE2'),
                        (7, 'Grants', 'FF69B4'),
                        (8, 'Royalties', 'FF1493'),
                        (9, 'Other', '808080');
                        """.trimIndent()
                    )

                    connection.execSQL(
                        """
                        INSERT OR IGNORE INTO expense_categories (categoryId, note, colorId) VALUES
                        (1, 'Groceries', '9ACD32'),
                        (2, 'Utilities', '6DCFF6'),
                        (3, 'Household', 'FFBF00'),
                        (4, 'Transportation', '506987'),
                        (5, 'Dining Out', 'FF6F61'),
                        (6, 'Entertainment', 'FF4040'),
                        (7, 'Gifts', '967BB6'),
                        (8, 'Clothing and Accessories', 'FF004D'),
                        (9, 'Personal Care', 'FFDAB9'),
                        (10, 'Technology and Electronics', '387ADF'),
                        (11, 'Investments', 'FFD700'),
                        (12, 'Unique', '008080'),
                        (13, 'Other', 'C7C8CC');
                        """.trimIndent()
                    )
                }
            }
        )
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}
