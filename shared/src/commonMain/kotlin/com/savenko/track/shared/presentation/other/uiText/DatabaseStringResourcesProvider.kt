package com.savenko.track.shared.presentation.other.uiText

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*
import com.savenko.track.shared.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.shared.domain.models.expenses.ExpenseCategory
import org.jetbrains.compose.resources.StringResource

/**
 * Use DatabaseStringResourcesProvider to provide string resources for predefined Room database
 */
class DatabaseStringResourcesProvider {
    fun provideDefaultCategoriesStringResource(category: CategoryEntity): StringResource {
        return if (category is ExpenseCategory) {
            expenseCategoryStringResources[category.categoryId] ?: Res.string.unknown
        } else {
            incomeCategoryStringResources[category.categoryId] ?: Res.string.unknown
        }
    }

    fun provideCurrencyStringResource(ticker: String): StringResource {
        return currencyStringResources[ticker] ?: Res.string.unknown
    }

    private val expenseCategoryStringResources = mapOf(
        1 to Res.string.expense_categories_groceries,
        2 to Res.string.expense_categories_Utilities,
        3 to Res.string.expense_categories_Household,
        4 to Res.string.expense_categories_Transportation,
        5 to Res.string.expense_categories_DiningOut,
        6 to Res.string.expense_categories_Entertainment,
        7 to Res.string.expense_categories_Gifts,
        8 to Res.string.expense_categories_ClothingAndAccessories,
        9 to Res.string.expense_categories_PersonalCare,
        10 to Res.string.expense_categories_TechnologyAndElectronics,
        11 to Res.string.expense_categories_Investments,
        12 to Res.string.expense_categories_Unique,
        13 to Res.string.expense_categories_Other
    )

    private val incomeCategoryStringResources = mapOf(
        1 to Res.string.income_categories_Salary,
        2 to Res.string.income_categories_Freelance,
        3 to Res.string.income_categories_Investment,
        4 to Res.string.income_categories_RentalIncome,
        5 to Res.string.income_categories_BusinessIncome,
        6 to Res.string.income_categories_Dividends,
        7 to Res.string.income_categories_Grants,
        8 to Res.string.income_categories_Royalties,
        9 to Res.string.other
    )

    private val currencyStringResources = mapOf(
        "AED" to Res.string.currency_uae_dirham,
        "ARS" to Res.string.currency_argentine_peso,
        "AUD" to Res.string.currency_australian_dollar,
        "BRL" to Res.string.currency_brazilian_real,
        "CAD" to Res.string.currency_canadian_dollar,
        "CHF" to Res.string.currency_swiss_franc,
        "CLP" to Res.string.currency_chilean_peso,
        "CNY" to Res.string.currency_chinese_yuan,
        "COP" to Res.string.currency_colombian_peso,
        "CZK" to Res.string.currency_czech_koruna,
        "DKK" to Res.string.currency_danish_krone,
        "EGP" to Res.string.currency_egyptian_pound,
        "EUR" to Res.string.currency_euro,
        "GBP" to Res.string.currency_british_pound_sterling,
        "HKD" to Res.string.currency_hong_kong_dollar,
        "HUF" to Res.string.currency_hungarian_forint,
        "IDR" to Res.string.currency_indonesian_rupiah,
        "ILS" to Res.string.currency_israeli_new_shekel,
        "INR" to Res.string.currency_indian_rupee,
        "JPY" to Res.string.currency_japanese_yen,
        "KRW" to Res.string.currency_south_korean_won,
        "MXN" to Res.string.currency_mexican_peso,
        "MYR" to Res.string.currency_malaysian_ringgit,
        "NGN" to Res.string.currency_nigerian_naira,
        "NOK" to Res.string.currency_norwegian_krone,
        "NZD" to Res.string.currency_new_zealand_dollar,
        "PEN" to Res.string.currency_peruvian_sol,
        "PHP" to Res.string.currency_philippine_peso,
        "PKR" to Res.string.currency_pakistani_rupee,
        "PLN" to Res.string.currency_polish_zloty,
        "RUB" to Res.string.currency_russian_ruble,
        "SAR" to Res.string.currency_saudi_riyal,
        "SEK" to Res.string.currency_swedish_krona,
        "SGD" to Res.string.currency_singapore_dollar,
        "THB" to Res.string.currency_thai_baht,
        "TRY" to Res.string.currency_turkish_lira,
        "TWD" to Res.string.currency_new_taiwan_dollar,
        "UAH" to Res.string.currency_ukrainian_hryvnia,
        "USD" to Res.string.currency_united_states_dollar,
        "ZAR" to Res.string.currency_south_african_rand,
        "ADA" to Res.string.currency_cardano,
        "BCH" to Res.string.currency_bitcoin_cash,
        "BNB" to Res.string.currency_binance_coin,
        "BTC" to Res.string.currency_bitcoin,
        "BUSD" to Res.string.currency_binance_usd,
        "DOGE" to Res.string.currency_dogecoin,
        "DOT" to Res.string.currency_polkadot,
        "ETH" to Res.string.currency_ethereum,
        "LTC" to Res.string.currency_litecoin,
        "SHIB" to Res.string.currency_shiba_inu,
        "SOL" to Res.string.currency_solana,
        "TRX" to Res.string.currency_tron,
        "USDC" to Res.string.currency_usd_coin,
        "USDT" to Res.string.currency_tether,
        "XRP" to Res.string.currency_ripple,
        "XAU" to Res.string.currency_gold,
        "XAG" to Res.string.currency_silver
    )
}
