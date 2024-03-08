package com.example.track.domain.repository.incomes

import com.example.track.data.models.incomes.IncomeCategory
import com.example.track.data.models.incomes.IncomeItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import java.util.Date
import kotlin.coroutines.CoroutineContext

interface IncomesListRepository {
    fun getIncomesList(): Flow<List<IncomeItem>>
    fun getIncomesItem(expensesItemId: Int): IncomeItem?
    suspend fun addIncomeItem(currentExpensesItem: IncomeItem, context: CoroutineContext = Dispatchers.IO)
    suspend fun deleteIncomeItem(currentExpenseItem: IncomeItem, context: CoroutineContext = Dispatchers.IO)
    suspend fun editIncomeItem(newExpenseItem: IncomeItem, context: CoroutineContext = Dispatchers.IO)
    fun getSortedIncomesListDateAsc(): Flow<List<IncomeItem>>
    fun getSortedIncomesListDateDesc(): Flow<List<IncomeItem>>
    fun getIncomesByCategoryInTimeSpan(startOfSpan: Date, endOfSpan: Date, category: IncomeCategory): List<IncomeItem>
}