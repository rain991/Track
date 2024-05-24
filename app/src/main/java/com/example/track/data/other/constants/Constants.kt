package com.example.track.data.other.constants

import com.example.track.domain.models.currency.CurrenciesPreference
import com.example.track.domain.models.currency.Currency
import com.example.track.domain.models.currency.CurrencyTypes
import com.example.track.presentation.themes.Themes

// Retrofit
const val API_KEY = "88441b2e585647a7842c35c23251558f"
const val CURRENCY_CALL_URL_DEFAULT = "https://api.currencyfreaks.com"

// DEFAULTS
const val NAME_DEFAULT: String = "User"
const val LOGIN_COUNT_DEFAULT = 0
const val NEEDS_LOGIN: Boolean = true
const val BUDGET_DEFAULT: Float = 0.0f
const val SHOW_PAGE_NAME_DEFAULT: Boolean = true
const val USE_SYSTEM_THEME_DEFAULT: Boolean = true
val PREFERABLE_THEME_DEFAULT: Themes = Themes.BlueTheme
val CURRENCY_DEFAULT = Currency(ticker = "USD", name = "United States Dollar", CurrencyTypes.DEFAULT, rate = null)
val IDEA_NOTE_MAX_LENGTH = 60




// ideas feed
const val FEED_CARD_DELAY_SLOW = 4500L
const val FEED_CARD_DELAY_FAST = 3300L

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
const val FIRST_VISIBLE_INDEX_SCROLL_BUTTON_APPEARANCE = 6
const val FIRST_VISIBLE_INDEX_FEED_DISSAPEARANCE = 8
const val MIN_SUPPORTED_YEAR = 2000
const val INCORRECT_CONVERSION_RESULT = -1f