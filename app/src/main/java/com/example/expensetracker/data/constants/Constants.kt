package com.example.expensetracker.data.constants

import com.example.expensetracker.data.models.currency.Currency
import com.example.expensetracker.data.models.currency.CurrencyTypes

// Other constants (objects of class as Currency) in DataStoreManager
const val DB_PATH = "database/expense_tracker.db"

const val NAME_DEFAULT: String = "User"
const val LOGIN_COUNT_DEFAULT = 0
const val NEEDS_LOGIN: Boolean = true
const val BUDGET_DEFAULT: Int = 0
val CURRENCY_DEFAULT = Currency(ticker = "USD", name = "United States Dollar", CurrencyTypes.DEFAULT)

