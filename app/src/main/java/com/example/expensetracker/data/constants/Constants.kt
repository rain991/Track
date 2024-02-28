package com.example.expensetracker.data.constants

import com.example.expensetracker.data.models.currency.Currency
import com.example.expensetracker.data.models.currency.CurrencyTypes

// Other constants (objects of class as Currency) in DataStoreManager
const val DB_PATH = "database/expense_tracker.db"
const val MIN_SUPPORTED_YEAR = 2000

// user
const val NAME_DEFAULT: String = "User"
const val LOGIN_COUNT_DEFAULT = 0
const val NEEDS_LOGIN: Boolean = true
const val BUDGET_DEFAULT: Int = 0
val CURRENCY_DEFAULT = Currency(ticker = "USD", name = "United States Dollar", CurrencyTypes.DEFAULT)

// ideas feed
const val BASIC_CARD_COUNT_IN_FEED = 2
const val FEED_CARD_DELAY_SLOW = 6000L
const val FEED_CARD_DELAY_FAST = 4000L

