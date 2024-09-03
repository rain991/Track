package com.savenko.track.domain.repository.incomes

import com.savenko.track.domain.models.incomes.IncomeCategory
import com.savenko.track.domain.models.incomes.IncomeItem
import kotlinx.coroutines.flow.Flow

interface IncomeListRepository {
    fun getIncomesList(): Flow<List<IncomeItem>>
    fun getSortedIncomesListDateAsc(): Flow<List<IncomeItem>>
    fun getSortedIncomesListDateDesc(): Flow<List<IncomeItem>>
    suspend fun getIncomesByCategoryInTimeSpan(startOfSpan: Long, endOfSpan: Long, category: IncomeCategory): Flow<List<IncomeItem>>
    suspend fun getIncomesInTimeSpanDateDesc(startOfSpan: Long, endOfSpan: Long): Flow<List<IncomeItem>>
}