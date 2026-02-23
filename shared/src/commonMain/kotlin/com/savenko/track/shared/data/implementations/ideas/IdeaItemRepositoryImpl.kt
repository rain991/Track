package com.savenko.track.shared.data.implementations.ideas

import com.savenko.track.shared.data.database.ideas.ExpenseLimitsDao
import com.savenko.track.shared.data.database.ideas.IncomePlansDao
import com.savenko.track.shared.data.database.ideas.SavingsDao
import com.savenko.track.shared.domain.models.abstractLayer.Idea
import com.savenko.track.shared.domain.models.idea.ExpenseLimits
import com.savenko.track.shared.domain.models.idea.IncomePlans
import com.savenko.track.shared.domain.models.idea.Savings
import com.savenko.track.shared.domain.repository.ideas.objectsRepository.IdeaItemRepository
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