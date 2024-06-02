package com.example.track.data.implementations.incomes

import com.example.track.data.database.incomeRelated.IncomeDao
import com.example.track.domain.models.incomes.IncomeCategory
import com.example.track.domain.models.incomes.IncomeItem
import com.example.track.domain.repository.incomes.IncomesListRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date

class IncomeListRepositoryImpl(private val incomeDao: IncomeDao) : IncomesListRepository {
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
}