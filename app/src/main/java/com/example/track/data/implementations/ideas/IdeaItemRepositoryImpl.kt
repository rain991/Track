package com.example.track.data.implementations.ideas

import com.example.track.data.database.ideaRelated.ExpenseLimitsDao
import com.example.track.data.database.ideaRelated.IncomePlansDao
import com.example.track.data.database.ideaRelated.SavingsDao
import com.example.track.domain.models.abstractLayer.Idea
import com.example.track.domain.models.idea.ExpenseLimits
import com.example.track.domain.models.idea.IncomePlans
import com.example.track.domain.models.idea.Savings
import com.example.track.domain.repository.ideas.objectsRepository.IdeaItemRepository
import kotlin.coroutines.CoroutineContext

class IdeaItemRepositoryImpl(
    private val expenseLimitsDao: ExpenseLimitsDao,
    private val incomePlansDao: IncomePlansDao,
    private val savingsDao: SavingsDao,
) : IdeaItemRepository {


    override suspend fun addIdea(idea: Idea, context: CoroutineContext) {
        when (idea) {
            is ExpenseLimits -> expenseLimitsDao.insert(idea)
            is IncomePlans -> incomePlansDao.insert(idea)
            is Savings -> savingsDao.insert(idea)
        }
    }

    override suspend fun updateIdea(idea: Idea) {
        when (idea) {
            is ExpenseLimits -> {
                expenseLimitsDao.update(idea)
            }

            is IncomePlans -> {
                incomePlansDao.update(idea)
            }

            is Savings -> {
                savingsDao.update(idea)
            }
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