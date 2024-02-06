package com.example.expensetracker.data.models

import com.example.expensetracker.R

class Currency(val ticker: String, val imageResourceId: Int) {
    internal fun addNewCurrency(ticker: String, imageResourceId: Int) {  // internal - accesible only in its package
        Currency(ticker, imageResourceId)
    }

    fun Currency.isReal() {
        TODO("Check for reality of currency. Could be only network call in app. So I am not sure about it")
    }
}

val USD = Currency("USD", R.drawable.usa96)
val EUR = Currency("EUR", R.drawable.europeanunion96)
val GBP = Currency("GBP", R.drawable.greatbritain96)
val UAH = Currency("UAH", R.drawable.ukraine96)
val CZK = Currency("CZK", R.drawable.czechrepublic96)


val currencyList = mutableListOf<Currency>(USD, EUR, GBP, UAH, CZK)