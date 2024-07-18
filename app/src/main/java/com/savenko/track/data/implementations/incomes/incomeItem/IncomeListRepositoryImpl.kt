package com.savenko.track.data.implementations.incomes.incomeItem

import com.savenko.track.data.database.incomeRelated.IncomeDao
import com.savenko.track.domain.models.incomes.IncomeCategory
import com.savenko.track.domain.models.incomes.IncomeItem
import com.savenko.track.domain.repository.incomes.IncomeListRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date

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
        startOfSpan: Date,
        endOfSpan: Date,
        category: IncomeCategory
    ): Flow<List<IncomeItem>> {
        return incomeDao.findIncomesInTimeSpanByCategoriesIds(
            start = startOfSpan.time,
            end = endOfSpan.time,
            categoriesIds = listOf(category.categoryId)
        )
    }

    override suspend fun getIncomesInTimeSpanDateDesc(
        startOfSpan: Date,
        endOfSpan: Date
    ): Flow<List<IncomeItem>> {
        return incomeDao.getIncomesInTimeSpanDateDecs(start = startOfSpan.time, end = endOfSpan.time)
    }
}