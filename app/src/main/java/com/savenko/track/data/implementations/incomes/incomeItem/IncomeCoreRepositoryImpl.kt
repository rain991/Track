package com.savenko.track.data.implementations.incomes.incomeItem

import com.savenko.track.data.core.CurrenciesRatesHandler
import com.savenko.track.data.database.incomeRelated.IncomeDao
import com.savenko.track.data.other.constants.INCORRECT_CONVERSION_RESULT
import com.savenko.track.domain.repository.currencies.CurrenciesPreferenceRepository
import com.savenko.track.domain.repository.incomes.IncomeCoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import java.util.Date

class IncomeCoreRepositoryImpl(
    private val incomeDao: IncomeDao,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepository,
    private val currenciesRatesHandler: CurrenciesRatesHandler
) : IncomeCoreRepository {
    // Sum of incomes
    override suspend fun getSumOfIncomesInTimeSpan(startOfSpan: Date, endOfSpan: Date): Flow<Float> = channelFlow {
        val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
        incomeDao.getIncomesInTimeSpanDateDecs(
            start = startOfSpan.time,
            end = endOfSpan.time
        ).collect { foundedIncomeItems ->
            var sumOfIncomesInPreferableCurrency = 0.0f
            val listOfIncomesInPreferableCurrency =
                foundedIncomeItems.filter { it.currencyTicker == preferableCurrency.ticker }
            val listOfIncomesNotInPreferableCurrency =
                foundedIncomeItems.filter { it.currencyTicker != preferableCurrency.ticker }
            listOfIncomesInPreferableCurrency.forEach { it -> sumOfIncomesInPreferableCurrency += it.value }
            listOfIncomesNotInPreferableCurrency.forEach { it ->
                val convertedValue = currenciesRatesHandler.convertValueToBasicCurrency(it)
                if (convertedValue != INCORRECT_CONVERSION_RESULT) {
                    sumOfIncomesInPreferableCurrency += convertedValue
                }
                // could be broadcast for insufficient currencies rates
            }
            send(sumOfIncomesInPreferableCurrency)
        }
    }

    override suspend fun getSumOfIncomesInTimeSpanByCategoriesIds(
        startOfSpan: Date,
        endOfSpan: Date,
        categoriesIds: List<Int>
    ): Flow<Float> = channelFlow {
        val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
        incomeDao.findIncomesInTimeSpanByCategoriesIds(
            start = startOfSpan.time,
            end = endOfSpan.time, categoriesIds = categoriesIds
        ).collect { foundedIncomeItems ->
            var sumOfIncomesInPreferableCurrency = 0.0f
            val listOfIncomesInPreferableCurrency =
                foundedIncomeItems.filter { it.currencyTicker == preferableCurrency.ticker }
            val listOfIncomesNotInPreferableCurrency =
                foundedIncomeItems.filter { it.currencyTicker != preferableCurrency.ticker }
            listOfIncomesInPreferableCurrency.forEach { sumOfIncomesInPreferableCurrency += it.value }
            listOfIncomesNotInPreferableCurrency.forEach {
                val convertedValue = currenciesRatesHandler.convertValueToBasicCurrency(it)
                if (convertedValue != INCORRECT_CONVERSION_RESULT) {
                    sumOfIncomesInPreferableCurrency += convertedValue
                }
            }
            send(sumOfIncomesInPreferableCurrency)
        }
    }

    // Count of incomes
    override suspend fun getCountOfIncomesInSpan(startDate: Date, endDate: Date): Flow<Int> {
        return incomeDao.getCountOfIncomesInTimeSpan(start = startDate.time, end = endDate.time)
    }

    override suspend fun getCountOfIncomesInSpanByCategoriesIds(
        startDate: Date,
        endDate: Date,
        categoriesIds: List<Int>
    ): Flow<Int> {
        return incomeDao.getCountOfIncomesInTimeSpanByCategoriesIds(
            start = startDate.time.apply { this.minus(1) },
            end = endDate.time,
            categoriesIds = categoriesIds
        )
    }

    // Average - average throughout values
    override suspend fun getAverageInTimeSpan(startDate: Date, endDate: Date): Flow<Float> {
        return combine(getSumOfIncomesInTimeSpan(startDate,endDate), getCountOfIncomesInSpan(startDate,endDate)){
            sumOfIncomes, countOfIncomes ->
            sumOfIncomes.div(countOfIncomes)
        }
    }
}