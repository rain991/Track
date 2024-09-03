package com.savenko.track.data.implementations.incomes.incomeItem

import com.savenko.track.data.database.incomeRelated.IncomeDao
import com.savenko.track.domain.models.incomes.IncomeCategory
import com.savenko.track.domain.models.incomes.IncomeItem
import com.savenko.track.domain.repository.incomes.IncomeListRepository
import kotlinx.coroutines.flow.Flow

class IncomeListRepositoryImpl(private val incomeDao: IncomeDao) : IncomeListRepository {
    override fun getIncomesList(): Flow<List<IncomeItem>> {
        return incomeDao.getAllIncomes()
    }

    override fun getSortedIncomesListDateAsc(): Flow<List<IncomeItem>> {
        return incomeDao.getAllWithDateAsc()
    }

    override fun getSortedIncomesListDateDesc(): Flow<List<IncomeItem>> {
        return incomeDao.getAllWithDateDesc()
    }

    override suspend fun getIncomesByCategoryInTimeSpan(
        startOfSpan: Long,
        endOfSpan: Long,
        category: IncomeCategory
    ): Flow<List<IncomeItem>> {
        return incomeDao.findIncomesInTimeSpanByCategoriesIds(
            start = startOfSpan,
            end = endOfSpan,
            categoriesIds = listOf(category.categoryId)
        )
    }

    override suspend fun getIncomesInTimeSpanDateDesc(startOfSpan: Long, endOfSpan: Long): Flow<List<IncomeItem>> {
        return incomeDao.getIncomesInTimeSpanDateDesc(start = startOfSpan, end = endOfSpan)
    }
}