package com.savenko.track.shared.data.implementations.incomes.incomeItem

import com.savenko.track.shared.data.core.CurrenciesRatesHandler
import com.savenko.track.shared.data.database.incomeRelated.IncomeDao
import com.savenko.track.shared.data.other.constants.INCORRECT_CONVERSION_RESULT
import com.savenko.track.shared.domain.repository.currencies.CurrenciesPreferenceRepository
import com.savenko.track.shared.domain.repository.incomes.IncomeCoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlin.time.Instant

class IncomeCoreRepositoryImpl(
    private val incomeDao: IncomeDao,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepository,
    private val currenciesRatesHandler: CurrenciesRatesHandler
) : IncomeCoreRepository {
    override fun getSumOfIncomesInTimeSpan(
        startOfSpan: Instant,
        endOfSpan: Instant
    ): Flow<Float> = flow {
        val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
        incomeDao.getIncomesInTimeSpanDateDesc(
            start = startOfSpan.toEpochMilliseconds(),
            end = endOfSpan.toEpochMilliseconds()
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
                // could be broadcast for insufficient currencies rates
            }
            emit(sumOfIncomesInPreferableCurrency)
        }
    }

    override fun getSumOfIncomesInTimeSpanByCategoriesIds(
        startOfSpan: Instant,
        endOfSpan: Instant,
        categoriesIds: List<Int>
    ): Flow<Float> = channelFlow {
        val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
        incomeDao.findIncomesInTimeSpanByCategoriesIds(
            start = startOfSpan.toEpochMilliseconds(),
            end = endOfSpan.toEpochMilliseconds(), categoriesIds = categoriesIds
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

    override fun getCountOfIncomesInSpan(
        startDate: Instant,
        endDate: Instant
    ): Flow<Int> {
        return incomeDao.getCountOfIncomesInTimeSpan(
            start = startDate.toEpochMilliseconds(),
            end = endDate.toEpochMilliseconds()
        )
    }

    override fun getCountOfIncomesInSpanByCategoriesIds(
        startDate: Instant,
        endDate: Instant,
        categoriesIds: List<Int>
    ): Flow<Int> {
        return incomeDao.getCountOfIncomesInTimeSpanByCategoriesIds(
            start = startDate.toEpochMilliseconds().apply { this.minus(1) },
            end = endDate.toEpochMilliseconds(),
            categoriesIds = categoriesIds
        )
    }

    override fun getAverageInTimeSpan(
        startDate: Instant,
        endDate: Instant
    ): Flow<Float> {
        return combine(
            getSumOfIncomesInTimeSpan(startDate, endDate),
            getCountOfIncomesInSpan(startDate, endDate)
        ) { sumOfIncomes, countOfIncomes ->
            sumOfIncomes.div(countOfIncomes)
        }
    }
}