package com.example.track.domain.repository.ideas.objectsRepository

import com.example.track.domain.models.abstractLayer.Idea
import com.example.track.domain.models.currency.Currency
import com.example.track.domain.models.idea.ExpenseLimits
import com.example.track.domain.models.idea.IncomePlans
import com.example.track.domain.models.idea.Savings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

interface IdeaListRepository {
    suspend fun getIncomesPlansList(context: CoroutineContext = Dispatchers.IO): Flow<List<IncomePlans>>
    suspend fun getExpenseLimitsList(context: CoroutineContext = Dispatchers.IO): Flow<List<ExpenseLimits>>
    suspend fun getSavingsList(context: CoroutineContext = Dispatchers.IO): Flow<List<Savings>>
    suspend fun getCompletionValue(idea: Idea): Flow<Float>
    suspend fun changePreferableCurrenciesOnIdeas(newPreferableCurrency: Currency, previousPreferableCurrency: Currency)
    suspend fun getCountOfIdeas(): Int
}