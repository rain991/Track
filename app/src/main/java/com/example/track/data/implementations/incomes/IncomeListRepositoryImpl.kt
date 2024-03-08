package com.example.track.data.implementations.incomes

import com.example.track.data.models.incomes.IncomeCategory
import com.example.track.data.models.incomes.IncomeItem
import com.example.track.domain.repository.incomes.IncomesListRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date
import kotlin.coroutines.CoroutineContext

class IncomeListRepositoryImpl : IncomesListRepository {
    override fun getIncomesList(): Flow<List<IncomeItem>> {
        TODO("Not yet implemented")
    }

    override fun getIncomesItem(expensesItemId: Int): IncomeItem? {
        TODO("Not yet implemented")
    }

    override suspend fun addIncomeItem(currentExpensesItem: IncomeItem, context: CoroutineContext) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteIncomeItem(currentExpenseItem: IncomeItem, context: CoroutineContext) {
        TODO("Not yet implemented")
    }

    override suspend fun editIncomeItem(newExpenseItem: IncomeItem, context: CoroutineContext) {
        TODO("Not yet implemented")
    }

    override fun getSortedIncomesListDateAsc(): Flow<List<IncomeItem>> {
        TODO("Not yet implemented")
    }

    override fun getSortedIncomesListDateDesc(): Flow<List<IncomeItem>> {
        TODO("Not yet implemented")
    }

    override fun getIncomesByCategoryInTimeSpan(startOfSpan: Date, endOfSpan: Date, category: IncomeCategory): List<IncomeItem> {
        TODO("Not yet implemented")
    }
}