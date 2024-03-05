package com.example.expensetracker.domain.repository

import com.example.expensetracker.data.models.currency.Currency

interface CurrenciesPreferenceRepository {
suspend fun setPreferableCurrency(currency : Currency)
}