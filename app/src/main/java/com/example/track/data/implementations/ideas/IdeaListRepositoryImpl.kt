package com.example.track.data.implementations.ideas

import com.example.track.data.database.ideaRelated.ExpenseLimitsDao
import com.example.track.data.database.ideaRelated.IncomePlansDao
import com.example.track.data.database.ideaRelated.SavingsDao
import com.example.track.data.implementations.expenses.ExpensesCoreRepositoryImpl
import com.example.track.data.implementations.incomes.IncomeCoreRepositoryImpl
import com.example.track.data.other.converters.convertLocalDateToDate
import com.example.track.data.other.converters.getEndOfTheMonth
import com.example.track.domain.models.abstractLayer.Idea
import com.example.track.domain.models.idea.ExpenseLimits
import com.example.track.domain.models.idea.IncomePlans
import com.example.track.domain.models.idea.Savings
import com.example.track.domain.repository.ideas.IdeaListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.time.LocalDate
import kotlin.coroutines.CoroutineContext

class IdeaListRepositoryImpl(
    private val expenseLimitsDao: ExpenseLimitsDao,
    private val incomePlansDao: IncomePlansDao,
    private val expensesCoreRepositoryImpl: ExpensesCoreRepositoryImpl,
    private val incomeCoreRepositoryImpl: IncomeCoreRepositoryImpl,
    private val savingsDao: SavingsDao
) : IdeaListRepository {
    override suspend fun getIncomesPlansList(context: CoroutineContext): Flow<List<IncomePlans>> {
        return withContext(context) {
            incomePlansDao.getAllData()
        }
    }

    override suspend fun getExpenseLimitsList(context: CoroutineContext): Flow<List<ExpenseLimits>> {
        return withContext(context) {
            expenseLimitsDao.getAllData()
        }
    }

    override suspend fun getSavingsList(context: CoroutineContext): Flow<List<Savings>> {
        return withContext(context) {
            savingsDao.getAllData()
        }
    }

    override suspend fun getCompletionValue(idea: Idea): Flow<Float> = flow {
        val currentTimeMillis = System.currentTimeMillis()
        withContext(Dispatchers.IO) {
            when (idea) {
                is ExpenseLimits -> {
                    if (idea.isRelatedToAllCategories) {
                        expensesCoreRepositoryImpl.getSumOfExpenses(idea.startDate.time, currentTimeMillis).collect {
                            emit(it)
                        }
                    } else {
                        val relatedGroups =
                            listOfNotNull(idea.firstRelatedCategoryId, idea.secondRelatedCategoryId, idea.thirdRelatedCategoryId)
                        expensesCoreRepositoryImpl.getSumOfExpensesByCategories(idea.startDate.time, currentTimeMillis, relatedGroups)
                    }
                }
                is IncomePlans -> {
                    incomeCoreRepositoryImpl.getSumOfIncomesInTimeSpan(
                        idea.startDate,
                        getEndOfTheMonth(convertLocalDateToDate(LocalDate.now()))
                    ).collect {
                        emit(it)
                    }
                }
                is Savings -> emit(idea.value)
            }
        }
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

    override suspend fun getCountOfIdeas(): Int {
        return (expenseLimitsDao.getCountOfExpenseLimits() + incomePlansDao.getCountOfIncomePlans() + savingsDao.getCountOfSavings())
    }
}