package com.example.expensetracker.data.implementations

import com.example.expensetracker.data.database.CurrenciesPreferenceDao
import com.example.expensetracker.data.models.currency.Currency
import com.example.expensetracker.domain.repository.CurrenciesPreferenceRepository

class CurrenciesPreferenceRepositoryImpl(private val currenciesPreferenceDao: CurrenciesPreferenceDao) : CurrenciesPreferenceRepository {
    override suspend fun setPreferableCurrency(currency : Currency) {
        currenciesPreferenceDao.updatePreferableCurrency(currency.ticker)
    }

}