package com.example.track.data.implementations.incomes

import com.example.track.data.core.CurrenciesRatesHandler
import com.example.track.data.database.incomeRelated.IncomeDao
import com.example.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.example.track.data.other.constants.INCORRECT_CONVERSION_RESULT
import com.example.track.domain.models.incomes.IncomeCategory
import com.example.track.domain.models.incomes.IncomeItem
import com.example.track.domain.repository.incomes.IncomesListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.util.Date
import kotlin.coroutines.CoroutineContext

class IncomeListRepositoryImpl(
    private val incomeDao: IncomeDao,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepositoryImpl,
    private val currenciesRatesHandler: CurrenciesRatesHandler
) : IncomesListRepository {
    override fun getIncomesList(): Flow<List<IncomeItem>> {
        return incomeDao.getAllIncomes()
    }

    override suspend fun getIncomesItem(incomeItemId: Int): IncomeItem? {
        return incomeDao.findIncomeById(incomeItemId)
    }

    override suspend fun addIncomeItem(currentIncomeItem: IncomeItem, context: CoroutineContext) {
        withContext(context) {
            incomeDao.insert(currentIncomeItem)
        }
    }

    override suspend fun deleteIncomeItem(currentIncomeItem: IncomeItem, context: CoroutineContext) {
        withContext(context) {
            incomeDao.delete(currentIncomeItem)
        }
    }

    override suspend fun editIncomeItem(newIncomeItem: IncomeItem, context: CoroutineContext) {
        withContext(context) {
            incomeDao.update(newIncomeItem)
        }
    }

    override fun getSortedIncomesListDateAsc(): Flow<List<IncomeItem>> {
        return incomeDao.getAllWithDateAsc()
    }

    override fun getSortedIncomesListDateDesc(): Flow<List<IncomeItem>> {
        return incomeDao.getAllWithDateDesc()
    }

    override suspend fun getIncomesByCategoryInTimeSpan(startOfSpan: Date, endOfSpan: Date, category: IncomeCategory): List<IncomeItem> {
        return incomeDao.findIncomesInTimeSpanByCategory(start = startOfSpan.time, end = endOfSpan.time, categoryId = category.categoryId)
    }

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
    override suspend fun getCountOfIncomesInSpan(startDate: Date, endDate: Date): Int {
        return incomeDao.getCountOfIncomesInTimeSpan(start = startDate.time, end = endDate.time)
    }
}