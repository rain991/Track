@file:Suppress("unused", "unused", "unused", "unused")

package com.savenko.track.data.other.constants

import androidx.compose.ui.graphics.Color
import java.text.DecimalFormat

/*
  Constants file contains constants used all across the Track app
  To see constants for specific components and default values check EntitiesDefaults.kt
 */

// Track default
const val DB_PATH = "database/expense_tracker.db"
const val INCORRECT_CONVERSION_RESULT = -1f
val LIST_OF_DEFAULT_EXPENSE_CATEGORIES_IDS = (1..13).toList()
val LIST_OF_DEFAULT_INCOMES_CATEGORIES_IDS = (1..9).toList()

// Retrofit
const val CURRENCY_CALL_URL_DEFAULT = "https://api.currencyfreaks.com"
const val CURRENCIES_RATES_REQUEST_PERIOD = 1L // days
/**
 *  Means if more then [ACCEPTABLE_EMPTY_CURRENCIES_RATES] currencies with empty rate exists,
 *  TrackActivity with use of WorkManagerHelper will call worker to receive new rates
 */
const val ACCEPTABLE_EMPTY_CURRENCIES_RATES = 0.7f

// Ideas specific colors
val incomePlanSpecificColor = Color(0xFFFFBF00)
val expenseLimitSpecificColor = Color(0xFFFF2400)
val savingsSpecificColor = Color(0xFF38CB82)

// Idea feed
const val FEED_CARD_DELAY_SLOW = 4500L
const val FEED_CARD_DELAY_FAST = 3300L
const val FEED_CARD_DELAY_ADDITIONAL = 2400L

const val FIRST_VISIBLE_INDEX_SCROLL_BUTTON_APPEARANCE = 6
const val FIRST_VISIBLE_INDEX_FEED_DISAPPEARANCE = 8

// Format constants
val FIAT_DECIMAL_FORMAT = DecimalFormat("#.##")
val CRYPTO_DECIMAL_FORMAT = DecimalFormat("#.####")
val PERCENTAGE_DECIMAL_FORMAT = DecimalFormat("#.##")

// Other constants
const val CURRENCIES_PREFERENCE_ID = 1
const val TAG = "Track_Log"

