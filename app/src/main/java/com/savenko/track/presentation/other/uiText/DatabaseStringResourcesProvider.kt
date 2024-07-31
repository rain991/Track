package com.savenko.track.presentation.UiText

import android.content.Context
import com.savenko.track.R
import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.domain.models.expenses.ExpenseCategory

class DatabaseStringResourcesProvider(private val context: Context) {
    fun provideDefaultCategoriesStringResource(category: CategoryEntity): Int {
        return if (category is ExpenseCategory) {
            expenseCategoryStringResources[category.categoryId] ?: R.string.unknown
        } else {
            incomeCategoryStringResources[category.categoryId] ?: R.string.unknown
        }
    }

    fun getCurrencyStringResource(ticker: String): Int {
        return currencyStringResources[ticker] ?: R.string.unknown
    }

    private val expenseCategoryStringResources = mapOf(
        1 to R.string.expense_categories_groceries,
        2 to R.string.expense_categories_Utilities,
        3 to R.string.expense_categories_Household,
        4 to R.string.expense_categories_Transportation,
        5 to R.string.expense_categories_DiningOut,
        6 to R.string.expense_categories_Entertainment,
        7 to R.string.expense_categories_Gifts,
        8 to R.string.expense_categories_ClothingAndAccessories,
        9 to R.string.expense_categories_PersonalCare,
        10 to R.string.expense_categories_TechnologyAndElectronics,
        11 to R.string.expense_categories_Investments,
        12 to R.string.expense_categories_Unique,
        13 to R.string.expense_categories_Other
    )

    private val incomeCategoryStringResources = mapOf(
        1 to R.string.income_categories_Salary,
        2 to R.string.income_categories_Freelance,
        3 to R.string.income_categories_Investment,
        4 to R.string.income_categories_RentalIncome,
        5 to R.string.income_categories_BusinessIncome,
        6 to R.string.income_categories_Dividends,
        7 to R.string.income_categories_Grants,
        8 to R.string.income_categories_Royalties,
        9 to R.string.other
    )

    private val currencyStringResources = mapOf(
        "AED" to R.string.currency_uae_dirham,
        "ARS" to R.string.currency_argentine_peso,
        "AUD" to R.string.currency_australian_dollar,
        "BRL" to R.string.currency_brazilian_real,
        "CAD" to R.string.currency_canadian_dollar,
        "CHF" to R.string.currency_swiss_franc,
        "CLP" to R.string.currency_chilean_peso,
        "CNY" to R.string.currency_chinese_yuan,
        "COP" to R.string.currency_colombian_peso,
        "CZK" to R.string.currency_czech_koruna,
        "DKK" to R.string.currency_danish_krone,
        "EGP" to R.string.currency_egyptian_pound,
        "EUR" to R.string.currency_euro,
        "GBP" to R.string.currency_british_pound_sterling,
        "HKD" to R.string.currency_hong_kong_dollar,
        "HUF" to R.string.currency_hungarian_forint,
        "IDR" to R.string.currency_indonesian_rupiah,
        "ILS" to R.string.currency_israeli_new_shekel,
        "INR" to R.string.currency_indian_rupee,
        "JPY" to R.string.currency_japanese_yen,
        "KRW" to R.string.currency_south_korean_won,
        "MXN" to R.string.currency_mexican_peso,
        "MYR" to R.string.currency_malaysian_ringgit,
        "NGN" to R.string.currency_nigerian_naira,
        "NOK" to R.string.currency_norwegian_krone,
        "NZD" to R.string.currency_new_zealand_dollar,
        "PEN" to R.string.currency_peruvian_sol,
        "PHP" to R.string.currency_philippine_peso,
        "PKR" to R.string.currency_pakistani_rupee,
        "PLN" to R.string.currency_polish_zloty,
        "RUB" to R.string.currency_russian_ruble,
        "SAR" to R.string.currency_saudi_riyal,
        "SEK" to R.string.currency_swedish_krona,
        "SGD" to R.string.currency_singapore_dollar,
        "THB" to R.string.currency_thai_baht,
        "TRY" to R.string.currency_turkish_lira,
        "TWD" to R.string.currency_new_taiwan_dollar,
        "UAH" to R.string.currency_ukrainian_hryvnia,
        "USD" to R.string.currency_united_states_dollar,
        "ZAR" to R.string.currency_south_african_rand,
        "ADA" to R.string.currency_cardano,
        "BCH" to R.string.currency_bitcoin_cash,
        "BNB" to R.string.currency_binance_coin,
        "BTC" to R.string.currency_bitcoin,
        "BUSD" to R.string.currency_binance_usd,
        "DOGE" to R.string.currency_dogecoin,
        "DOT" to R.string.currency_polkadot,
        "ETH" to R.string.currency_ethereum,
        "LTC" to R.string.currency_litecoin,
        "SHIB" to R.string.currency_shiba_inu,
        "SOL" to R.string.currency_solana,
        "TRX" to R.string.currency_tron,
        "USDC" to R.string.currency_usd_coin,
        "USDT" to R.string.currency_tether,
        "XRP" to R.string.currency_ripple,
        "XAU" to R.string.currency_gold,
        "XAG" to R.string.currency_silver
    )
}