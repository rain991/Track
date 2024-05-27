package com.example.track.data.implementations.ideas

import com.example.track.data.core.CurrenciesRatesHandler
import com.example.track.data.database.incomeRelated.IncomeDao
import com.example.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.example.track.data.implementations.incomes.IncomeListRepositoryImpl
import com.example.track.data.other.constants.INCORRECT_CONVERSION_RESULT
import com.example.track.data.other.converters.convertLocalDateToDate
import com.example.track.domain.models.abstractLayer.Idea
import com.example.track.domain.repository.ideas.IncomePlanCardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.util.Date

class IncomePlanCardRepositoryImpl(
    private val incomeDao: IncomeDao,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepositoryImpl,
    private val currenciesRatesHandler: CurrenciesRatesHandler, private val incomeListRepositoryImpl: IncomeListRepositoryImpl
) : IncomePlanCardRepository {
    override fun requestPlannedIncome(idea: Idea): Float {
        return idea.goal
    }

    override fun requestStartOfPeriod(idea: Idea): Date {
        return idea.startDate
    }

    override fun requestEndOfPeriod(idea: Idea): Date? {
        return idea.endDate
    }

    override suspend fun requestCompletenessValue(idea: Idea): Flow<Float> = flow {
        val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
        val todayDate = convertLocalDateToDate(LocalDate.now())
        incomeDao.getIncomesInTimeSpanDateDecs(
            start = idea.startDate.time,
            end = idea.endDate?.time ?: Long.MAX_VALUE
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

    override suspend fun requestCompletenessRate(idea: Idea): Flow<Float> = flow {
        requestCompletenessValue(idea).collect { completenessValue ->
            emit(completenessValue.div(idea.goal))
        }
    }

    override suspend fun getSumOfIncomesInTimeSpan(startOfSpan: Date, endOfSpan: Date): Flow<Float> {
        return incomeListRepositoryImpl.getSumOfIncomesInTimeSpan(startOfSpan, endOfSpan)
    }
}