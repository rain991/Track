package com.savenko.track.shared.domain.repository.ideas.objectsRepository

import com.savenko.track.shared.domain.models.abstractLayer.Idea
import com.savenko.track.shared.domain.models.currency.Currency
import com.savenko.track.shared.domain.models.idea.ExpenseLimits
import com.savenko.track.shared.domain.models.idea.IncomePlans
import com.savenko.track.shared.domain.models.idea.Savings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

interface IdeaListRepository {
    fun getIncomesPlansList(context: CoroutineContext = Dispatchers.Default): Flow<List<IncomePlans>>
    fun getExpenseLimitsList(context: CoroutineContext = Dispatchers.Default): Flow<List<ExpenseLimits>>
    fun getSavingsList(context: CoroutineContext = Dispatchers.Default): Flow<List<Savings>>
    fun getIdeaCompletedValue(idea: Idea): Flow<Float>
    suspend fun changePreferableCurrenciesOnIdeas(newPreferableCurrency: Currency, previousPreferableCurrency: Currency)
    suspend fun getCountOfIdeas(): Int
}