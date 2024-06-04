package com.example.track.data.implementations.incomes

import com.example.track.data.core.CurrenciesRatesHandler
import com.example.track.data.database.incomeRelated.IncomeDao
import com.example.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.example.track.data.other.constants.INCORRECT_CONVERSION_RESULT
import com.example.track.domain.repository.incomes.IncomeCoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.util.Date

class IncomeCoreRepositoryImpl(
    private val incomeDao: IncomeDao,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepositoryImpl,
    private val currenciesRatesHandler: CurrenciesRatesHandler
) : IncomeCoreRepository {

    // Sum of incomes
    override suspend fun getSumOfIncomesInTimeSpan(startOfSpan: Date, endOfSpan: Date): Flow<Float> = flow {
        val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
        incomeDao.getIncomesInTimeSpanDateDecs(
            start = startOfSpan.time,
            end = endOfSpan.time
        ).collect { foundedIncomeItems ->
            var sumOfIncomesInPreferableCurrency = 0.0f
            val listOfIncomesInPreferableCurrency = foundedIncomeItems.filter { it.currencyTicker == preferableCurrency.ticker }
            val listOfIncomesNotInPreferableCurrency = foundedIncomeItems.filter { it.currencyTicker != preferableCurrency.ticker }
            listOfIncomesInPreferableCurrency.forEach { it -> sumOfIncomesInPreferableCurrency += it.value }
            listOfIncomesNotInPreferableCurrency.forEach { it ->
                val convertedValue = currenciesRatesHandler.convertValueToBasicCurrency(it)
                if (convertedValue != INCORRECT_CONVERSION_RESULT) {
                    sumOfIncomesInPreferableCurrency += convertedValue
                }
            }
            emit(sumOfIncomesInPreferableCurrency)
        }
    }

    override suspend fun getSumOfIncomesInTimeSpanByCategoriesIds(
        startOfSpan: Date,
        endOfSpan: Date,
        categoriesIds: List<Int>
    ): Flow<Float> = flow {
        val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
        incomeDao.findIncomesInTimeSpanByCategoriesIds(
            start = startOfSpan.time,
            end = endOfSpan.time, categoriesIds = categoriesIds
        ).collect { foundedIncomeItems ->
            var sumOfIncomesInPreferableCurrency = 0.0f
            val listOfIncomesInPreferableCurrency = foundedIncomeItems.filter { it.currencyTicker == preferableCurrency.ticker }
            val listOfIncomesNotInPreferableCurrency = foundedIncomeItems.filter { it.currencyTicker != preferableCurrency.ticker }
            listOfIncomesInPreferableCurrency.forEach { it -> sumOfIncomesInPreferableCurrency += it.value }
            listOfIncomesNotInPreferableCurrency.forEach { it ->
                val convertedValue = currenciesRatesHandler.convertValueToBasicCurrency(it)
                if (convertedValue != INCORRECT_CONVERSION_RESULT) {
                    sumOfIncomesInPreferableCurrency += convertedValue
                }
            }
            emit(sumOfIncomesInPreferableCurrency)
        }
    }

    // Count of incomes
    override suspend fun getCountOfIncomesInSpan(startDate: Date, endDate: Date): Flow<Int> {
        return incomeDao.getCountOfIncomesInTimeSpan(start = startDate.time, end = endDate.time)
    }

    override suspend fun getCountOfIncomesInSpanByCategoriesIds(startDate: Date, endDate: Date, categoriesIds: List<Int>): Flow<Int> {
        return incomeDao.getCountOfIncomesInTimeSpanByCategoriesIds(
            start = startDate.time,
            end = endDate.time,
            categoriesIds = categoriesIds
        )
    }
}