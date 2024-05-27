package com.example.track.domain.repository.incomes

import com.example.track.domain.models.incomes.IncomeCategory
import com.example.track.domain.models.incomes.IncomeItem
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface IncomesListRepository {
    fun getIncomesList(): Flow<List<IncomeItem>>

    fun getSortedIncomesListDateAsc(): Flow<List<IncomeItem>>
    fun getSortedIncomesListDateDesc(): Flow<List<IncomeItem>>
    suspend fun getIncomesByCategoryInTimeSpan(startOfSpan: Date, endOfSpan: Date, category: IncomeCategory): List<IncomeItem>

}