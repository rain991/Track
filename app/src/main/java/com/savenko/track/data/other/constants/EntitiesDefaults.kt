package com.savenko.track.data.other.constants

import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.domain.models.currency.CurrencyTypes
import com.savenko.track.presentation.themes.Themes
import com.savenko.track.presentation.themes.purpleGreyTheme.purpleGreyNew_DarkColorScheme
import com.savenko.track.presentation.themes.purpleGreyTheme.purpleGreyNew_LightColorScheme

// User defaults
const val NAME_DEFAULT: String = "User"
const val LOGIN_COUNT_DEFAULT = 0
const val BUDGET_DEFAULT: Float = 0.0f
val CURRENCY_DEFAULT = Currency(ticker = "USD", name = "United States Dollar", CurrencyTypes.FIAT, rate = null)

// Preferences defaults
const val SHOW_PAGE_NAME_DEFAULT: Boolean = true
const val USE_SYSTEM_THEME_DEFAULT: Boolean = false
const val NON_CATEGORY_FINANCIALS_DEFAULT = false
const val GROUPING_CATEGORY_ID_DEFAULT = Int.MIN_VALUE // This value used to compare if user selected any category
const val EXPENSE_CATEGORY_GROUPING_ID_DEFAULT = 13
const val INCOME_CATEGORY_GROUPING_ID_DEFAULT = 9
const val UNINITIALIZED_LOGIN_COUNT_STATE = -1

// Material theme and colorscheme
val PREFERABLE_THEME_DEFAULT: Themes = Themes.PurpleGreyTheme
val DEFAULT_LIGHT_COLOR_SCHEME = purpleGreyNew_LightColorScheme
val DEFAULT_DARK_COLOR_SCHEME = purpleGreyNew_DarkColorScheme

// Size of default categories in predefined Room DB
const val DEFAULT_EXPENSE_CATEGORIES_MAX_INDEX = 13
const val DEFAULT_INCOME_CATEGORIES_MAX_INDEX = 9

// Max. components values
const val CATEGORIES_NAME_MAX_LENGTH = 40
const val IDEA_NOTE_MAX_LENGTH = 60
const val NAME_MAX_LENGTH = 40
const val FINANCIAL_NOTE_MAX_LENGTH = 100
const val CURRENCIES_FILTER_MAX_LENGTH = 40
const val MAX_BUDGET_VALUE = Int.MAX_VALUE
const val MAX_IDEA_VALUE = Int.MAX_VALUE
const val MAX_FINANCIAL_VALUE = Float.MAX_VALUE
const val MAX_CIRCULAR_PROGRESS_VALUE = 999f
const val FINANCIAL_CARD_NOTE_LENGTH_CONCATENATE = 16
const val MONTH_SUMMARY_MIN_LIST_SIZE =
    10 // defines min. size of list of FinancialEntity needed in MainScreenLazyColumn to show month summary

/** Changing this parameter will not increase real max value,
    it is only limit for composables
    (needed migration in Room from categories IDs parameters to list of categories IDs) */
const val EXPENSE_LIMIT_MAX_CATEGORIES_SELECTED = 3

