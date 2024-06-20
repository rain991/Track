package com.savenko.track.domain.repository.ideas.objectsRepository

import com.savenko.track.domain.models.abstractLayer.Idea
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.domain.models.idea.ExpenseLimits
import com.savenko.track.domain.models.idea.IncomePlans
import com.savenko.track.domain.models.idea.Savings
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