package com.example.track.data.constants

import com.example.track.data.models.currency.CurrenciesPreference
import com.example.track.data.models.currency.Currency
import com.example.track.data.models.currency.CurrencyTypes
import com.example.track.presentation.themes.Themes

// Retrofit
const val API_KEY = "88441b2e585647a7842c35c23251558f"
const val CURRENCY_CALL_URL_DEFAULT = "https://api.currencyfreaks.com"

// user
const val NAME_DEFAULT: String = "User"
const val LOGIN_COUNT_DEFAULT = 0
const val NEEDS_LOGIN: Boolean = true
const val BUDGET_DEFAULT: Int = 0
const val SHOW_PAGE_NAME_DEFAULT: Boolean = true
const val USE_SYSTEM_THEME_DEFAULT: Boolean = true
val PREFERABLE_THEME_DEFAULT: Themes = Themes.BlueTheme
val CURRENCY_DEFAULT = Currency(ticker = "USD", name = "United States Dollar", CurrencyTypes.DEFAULT, rate = null)

// ideas feed
const val BASIC_CARD_COUNT_IN_FEED = 2
const val FEED_CARD_DELAY_SLOW = 5000L
const val FEED_CARD_DELAY_FAST = 3600L

// Other constants (objects of class as Currency) in DataStoreManager
const val DB_PATH = "database/expense_tracker.db"
const val CURRENCIES_PREFERENCE_ID = 1
val CURRENCIES_PREFERENCE_DEFAULT = CurrenciesPreference(
    preferableCurrency = CURRENCY_DEFAULT.ticker,
    firstAdditionalCurrency = null,
    secondAdditionalCurrency = null,
    thirdAdditionalCurrency = null,
    fourthAdditionalCurrency = null
)
const val MIN_SUPPORTED_YEAR = 2000