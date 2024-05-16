package com.example.track.data.implementations.incomes

import com.example.track.data.database.incomeRelated.IncomeDao
import com.example.track.domain.models.incomes.IncomeCategory
import com.example.track.domain.models.incomes.IncomeItem
import com.example.track.domain.repository.incomes.IncomesListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.Date
import kotlin.coroutines.CoroutineContext

class IncomeListRepositoryImpl(private val incomeDao: IncomeDao) : IncomesListRepository {
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
}