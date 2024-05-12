package com.example.track.domain.repository.incomes

import com.example.track.domain.models.incomes.IncomeCategory
import com.example.track.domain.models.incomes.IncomeItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import java.util.Date
import kotlin.coroutines.CoroutineContext

interface IncomesListRepository {
    fun getIncomesList(): Flow<List<IncomeItem>>
    suspend fun getIncomesItem(incomeItemId: Int): IncomeItem?
    suspend fun addIncomeItem(currentIncomeItem: IncomeItem, context: CoroutineContext = Dispatchers.IO)
    suspend fun deleteIncomeItem(currentIncomeItem: IncomeItem, context: CoroutineContext = Dispatchers.IO)
    suspend fun editIncomeItem(newIncomeItem: IncomeItem, context: CoroutineContext = Dispatchers.IO)
    fun getSortedIncomesListDateAsc(): Flow<List<IncomeItem>>
    fun getSortedIncomesListDateDesc(): Flow<List<IncomeItem>>
    suspend fun getIncomesByCategoryInTimeSpan(startOfSpan: Date, endOfSpan: Date, category: IncomeCategory): List<IncomeItem>

}