package com.example.track.data.implementations.ideas

import com.example.track.data.database.ideaRelated.ExpenseLimitsDao
import com.example.track.data.database.ideaRelated.IncomePlansDao
import com.example.track.data.database.ideaRelated.SavingsDao
import com.example.track.data.models.idea.ExpenseLimits
import com.example.track.data.models.idea.Idea
import com.example.track.data.models.idea.IncomePlans
import com.example.track.data.models.idea.Savings
import com.example.track.domain.repository.ideas.IdeaListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import kotlin.coroutines.CoroutineContext

class IdeaListRepositoryImpl(
    private val expenseLimitsDao: ExpenseLimitsDao,
    private val incomePlansDao: IncomePlansDao,
    private val savingsDao: SavingsDao
) : IdeaListRepository {
    override suspend fun getIdeasList(context: CoroutineContext): Flow<List<Idea>> {
        return merge(expenseLimitsDao.getAllData(), incomePlansDao.getAllData(), savingsDao.getAllData())
    }

    override suspend fun addIdea(idea: Idea, context: CoroutineContext) {
        when (idea) {
            is ExpenseLimits -> expenseLimitsDao.insert(idea)
            is IncomePlans -> incomePlansDao.insert(idea)
            is Savings -> savingsDao.insert(idea)
        }
    }

    override suspend fun editIdea(idea: Idea, context: CoroutineContext) {
        when (idea) {
            is ExpenseLimits -> expenseLimitsDao.update(idea)
            is IncomePlans -> incomePlansDao.update(idea)
            is Savings -> savingsDao.update(idea)
        }
    }

    override suspend fun deleteIdea(idea: Idea, context: CoroutineContext) {
        when (idea) {
            is ExpenseLimits -> expenseLimitsDao.delete(idea)
            is IncomePlans -> incomePlansDao.delete(idea)
            is Savings -> savingsDao.delete(idea)
        }
    }
}