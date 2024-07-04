package com.savenko.track.data.implementations.ideas

import com.savenko.track.data.core.CurrenciesRatesHandler
import com.savenko.track.data.database.ideaRelated.ExpenseLimitsDao
import com.savenko.track.data.database.ideaRelated.IncomePlansDao
import com.savenko.track.data.database.ideaRelated.SavingsDao
import com.savenko.track.data.implementations.expenses.expenseItem.ExpensesCoreRepositoryImpl
import com.savenko.track.data.implementations.incomes.incomeItem.IncomeCoreRepositoryImpl
import com.savenko.track.data.other.converters.dates.convertLocalDateToDate
import com.savenko.track.data.other.converters.dates.getEndOfTheMonth
import com.savenko.track.domain.models.abstractLayer.Idea
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.domain.models.idea.ExpenseLimits
import com.savenko.track.domain.models.idea.IncomePlans
import com.savenko.track.domain.models.idea.Savings
import com.savenko.track.domain.repository.ideas.objectsRepository.IdeaListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.time.LocalDate
import kotlin.coroutines.CoroutineContext

class IdeaListRepositoryImpl(
    private val expenseLimitsDao: ExpenseLimitsDao,
    private val incomePlansDao: IncomePlansDao,
    private val savingsDao: SavingsDao,
    private val expensesCoreRepositoryImpl: ExpensesCoreRepositoryImpl,
    private val incomeCoreRepositoryImpl: IncomeCoreRepositoryImpl,
    private val currenciesRatesHandler: CurrenciesRatesHandler
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

    override suspend fun getCompletionValue(idea: Idea): Flow<Float> = channelFlow {
        val currentTimeMillis = System.currentTimeMillis()
        withContext(Dispatchers.IO) {
            when (idea) {
                is ExpenseLimits -> {
                    if (idea.isRelatedToAllCategories) {
                        expensesCoreRepositoryImpl.getSumOfExpenses(idea.startDate.time, currentTimeMillis).collect {
                            send(it)
                        }
                    } else {
                        val relatedGroups =
                            listOfNotNull(idea.firstRelatedCategoryId, idea.secondRelatedCategoryId, idea.thirdRelatedCategoryId)
                        expensesCoreRepositoryImpl.getSumOfExpensesByCategories(idea.startDate.time, currentTimeMillis, relatedGroups)
                            .collect {
                                send(it)
                            }
                    }
                }
                is IncomePlans -> {
                    incomeCoreRepositoryImpl.getSumOfIncomesInTimeSpan(
                        idea.startDate,
                        getEndOfTheMonth(convertLocalDateToDate(LocalDate.now()))
                    ).collect {
                        send(it)
                    }
                }

                is Savings -> send(idea.value)
            }
        }
    }

    override suspend fun changePreferableCurrenciesOnIdeas(newPreferableCurrency: Currency, previousPreferableCurrency: Currency) {
        val ideasList = mutableListOf<Idea>()
        ideasList.addAll(getIncomesPlansList().first())
        ideasList.addAll(getSavingsList().first())
        ideasList.addAll(getExpenseLimitsList().first())
        ideasList.forEach { idea ->
            when (idea) {
                is Savings -> {
                    val newValue =
                        currenciesRatesHandler.convertValueToAnyCurrency(idea.value, previousPreferableCurrency, newPreferableCurrency)
                    val newGoal =
                        currenciesRatesHandler.convertValueToAnyCurrency(idea.goal, previousPreferableCurrency, newPreferableCurrency)
                    savingsDao.update(idea.copy(value = newValue, goal = newGoal))
                }

                is IncomePlans -> {
                    val newGoal =
                        currenciesRatesHandler.convertValueToAnyCurrency(idea.goal, previousPreferableCurrency, newPreferableCurrency)
                    incomePlansDao.update(idea.copy(goal = newGoal))
                }

                is ExpenseLimits -> {
                    val newGoal =
                        currenciesRatesHandler.convertValueToAnyCurrency(idea.goal, previousPreferableCurrency, newPreferableCurrency)
                    expenseLimitsDao.update(idea.copy(goal = newGoal))
                }
            }
        }
    }

    override suspend fun getCountOfIdeas(): Int {
        return (expenseLimitsDao.getCountOfExpenseLimits() + incomePlansDao.getCountOfIncomePlans() + savingsDao.getCountOfSavings())
    }
}