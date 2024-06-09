package com.example.track.data.other.constants

import java.text.DecimalFormat

// Retrofit
const val API_KEY = "88441b2e585647a7842c35c23251558f"
const val CURRENCY_CALL_URL_DEFAULT = "https://api.currencyfreaks.com"

// Track defaults
const val DB_PATH = "database/expense_tracker.db"
const val MIN_SUPPORTED_YEAR = 2000
const val INCORRECT_CONVERSION_RESULT = -1f
val LIST_OF_DEFAULT_EXPENSE_CATEGORIES_IDS = (1..13).toList()
val LIST_OF_DEFAULT_INCOMES_CATEGORIES_IDS = (1..9).toList()

// ideas feed
const val FEED_CARD_DELAY_SLOW = 4500L
const val FEED_CARD_DELAY_FAST = 3300L
const val FEED_CARD_DELAY_ADDITIONAL = 2400L

const val FIRST_VISIBLE_INDEX_SCROLL_BUTTON_APPEARANCE = 6
const val FIRST_VISIBLE_INDEX_FEED_DISSAPEARANCE = 8

// Format constants
val FIAT_DECIMAL_FORMAT = DecimalFormat("#.##")
val CRYPTO_DECIMAL_FORMAT = DecimalFormat("#.####")
val OTHER_CURRENCY_TYPE_DECIMAL_FORMAT = DecimalFormat("#.###")
val PERCENTAGE_DECIMAL_FORMAT = DecimalFormat("#.##")

// Other constants
const val CURRENCIES_PREFERENCE_ID = 1