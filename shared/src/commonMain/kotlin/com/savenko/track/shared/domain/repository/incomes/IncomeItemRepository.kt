package com.savenko.track.shared.domain.repository.incomes

import com.savenko.track.shared.domain.models.incomes.IncomeItem
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface IncomeItemRepository {
    suspend fun getIncomesItem(incomeItemId: Int): IncomeItem?
    suspend fun addIncomeItem(currentIncomeItem: IncomeItem, context: CoroutineContext = Dispatchers.Default)
    suspend fun deleteIncomeItem(currentIncomeItem: IncomeItem, context: CoroutineContext = Dispatchers.Default)
    suspend fun editIncomeItem(newIncomeItem: IncomeItem, context: CoroutineContext = Dispatchers.Default)
}