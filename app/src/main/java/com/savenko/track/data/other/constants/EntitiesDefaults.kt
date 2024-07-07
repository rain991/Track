package com.savenko.track.data.other.constants

import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.domain.models.currency.CurrencyTypes
import com.savenko.track.presentation.themes.Themes

// User defaults
const val NAME_DEFAULT: String = "User"
const val LOGIN_COUNT_DEFAULT = 0
const val BUDGET_DEFAULT: Float = 0.0f
const val SHOW_PAGE_NAME_DEFAULT: Boolean = true
const val USE_SYSTEM_THEME_DEFAULT: Boolean = true
val PREFERABLE_THEME_DEFAULT: Themes = Themes.BlueTheme
const val NON_CATEGORY_FINANCIALS_DEFAULT = false
const val GROUPING_CATEGORY_ID_DEFAULT = Int.MIN_VALUE

// Entities defaults
val CURRENCY_DEFAULT = Currency(ticker = "USD", name = "United States Dollar", CurrencyTypes.FIAT, rate = null)
const val DEFAULT_EXPENSE_CATEGORIES_MAX_INDEX = 13
const val DEFAULT_INCOME_CATEGORIES_MAX_INDEX = 9

// Max entities values
const val CATEGORIES_NAME_MAX_LENGTH = 40
const val IDEA_NOTE_MAX_LENGTH = 60
const val NAME_MAX_LENGTH = 40
const val MAX_BUDGET_VALUE = Int.MAX_VALUE
const val MAX_IDEA_VALUE = Int.MAX_VALUE
const val CURRENCIES_RATES_REQUEST_PERIOD = 3L // days

/*  warning changing this parameter will not increase real max value,
 it is only limit for composables (needed migration in Room from categories IDs parameters to list of categories IDs) */
const val EXPENSE_LIMIT_MAX_CATEGORIES_SELECTED = 3
