package com.savenko.track.domain.repository.incomes

import com.savenko.track.domain.models.incomes.IncomeCategory
import com.savenko.track.domain.models.incomes.IncomeItem
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface IncomeListRepository {
    fun getIncomesList(): Flow<List<IncomeItem>>
    fun getSortedIncomesListDateAsc(): Flow<List<IncomeItem>>
    fun getSortedIncomesListDateDesc(): Flow<List<IncomeItem>>
    suspend fun getIncomesByCategoryInTimeSpan(startOfSpan: Date, endOfSpan: Date, category: IncomeCategory): Flow<List<IncomeItem>>
    suspend fun getIncomesInTimeSpanDateDesc(startOfSpan: Date, endOfSpan: Date): Flow<List<IncomeItem>>

}