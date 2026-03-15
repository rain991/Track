package com.savenko.track.shared.testdoubles

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import com.savenko.track.shared.data.database.currencies.CurrencyDao
import com.savenko.track.shared.data.database.expensesRelated.ExpenseItemsDAO
import com.savenko.track.shared.domain.models.abstractLayer.Idea
import com.savenko.track.shared.domain.models.currency.CurrenciesPreference
import com.savenko.track.shared.domain.models.currency.Currency
import com.savenko.track.shared.domain.models.currency.CurrencyTypes
import com.savenko.track.shared.domain.models.expenses.ExpenseCategory
import com.savenko.track.shared.domain.models.expenses.ExpenseItem
import com.savenko.track.shared.domain.models.idea.ExpenseLimits
import com.savenko.track.shared.domain.models.idea.IncomePlans
import com.savenko.track.shared.domain.models.idea.Savings
import com.savenko.track.shared.domain.models.incomes.IncomeCategory
import com.savenko.track.shared.domain.models.incomes.IncomeItem
import com.savenko.track.shared.domain.repository.currencies.CurrenciesPreferenceRepository
import com.savenko.track.shared.domain.repository.currencies.CurrenciesPreferenceConverted
import com.savenko.track.shared.domain.repository.expenses.ExpenseItemRepository
import com.savenko.track.shared.domain.repository.expenses.ExpensesCoreRepository
import com.savenko.track.shared.domain.repository.expenses.ExpensesListRepository
import com.savenko.track.shared.domain.repository.expenses.categories.ExpensesCategoriesListRepository
import com.savenko.track.shared.domain.repository.ideas.objectsRepository.IdeaItemRepository
import com.savenko.track.shared.domain.repository.ideas.objectsRepository.IdeaListRepository
import com.savenko.track.shared.domain.repository.incomes.IncomeCoreRepository
import com.savenko.track.shared.domain.repository.incomes.IncomeItemRepository
import com.savenko.track.shared.domain.repository.incomes.IncomeListRepository
import com.savenko.track.shared.domain.repository.incomes.categories.IncomesCategoriesListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlin.coroutines.CoroutineContext
import kotlin.time.Instant

fun testCurrency(
    ticker: String = "USD",
    rate: Double? = 1.0
): Currency = Currency(
    ticker = ticker,
    name = ticker,
    type = CurrencyTypes.FIAT,
    rate = rate
)

fun testIncomePlan(
    id: Int,
    completed: Boolean
): IncomePlans = IncomePlans(
    id = id,
    goal = 100f,
    completed = completed,
    startDate = 0L,
    endDate = null
)

fun testExpenseLimit(
    id: Int,
    completed: Boolean
): ExpenseLimits = ExpenseLimits(
    id = id,
    goal = 100f,
    completed = completed,
    startDate = 0L,
    endDate = null,
    isEachMonth = false,
    isRelatedToAllCategories = true,
    firstRelatedCategoryId = null,
    secondRelatedCategoryId = null,
    thirdRelatedCategoryId = null
)

fun testSavings(
    id: Int,
    completed: Boolean
): Savings = Savings(
    id = id,
    goal = 100f,
    completed = completed,
    startDate = 0L,
    endDate = null,
    label = "Savings",
    value = if (completed) 100f else 10f
)

class FakeExpenseItemRepository : ExpenseItemRepository {
    val added = mutableListOf<ExpenseItem>()
    val deleted = mutableListOf<ExpenseItem>()

    override suspend fun getExpenseItem(expensesItemId: Int): ExpenseItem? =
        added.find { it.id == expensesItemId }

    override suspend fun addExpensesItem(currentExpensesItem: ExpenseItem, context: CoroutineContext) {
        added += currentExpensesItem
    }

    override suspend fun deleteExpenseItem(currentExpenseItem: ExpenseItem, context: CoroutineContext) {
        deleted += currentExpenseItem
    }

    override suspend fun editExpenseItem(newExpenseItem: ExpenseItem, context: CoroutineContext) {
        val index = added.indexOfFirst { it.id == newExpenseItem.id }
        if (index >= 0) {
            added[index] = newExpenseItem
        }
    }
}

class FakeIncomeItemRepository : IncomeItemRepository {
    val added = mutableListOf<IncomeItem>()
    val deleted = mutableListOf<IncomeItem>()

    override suspend fun getIncomesItem(incomeItemId: Int): IncomeItem? =
        added.find { it.id == incomeItemId }

    override suspend fun addIncomeItem(currentIncomeItem: IncomeItem, context: CoroutineContext) {
        added += currentIncomeItem
    }

    override suspend fun deleteIncomeItem(currentIncomeItem: IncomeItem, context: CoroutineContext) {
        deleted += currentIncomeItem
    }

    override suspend fun editIncomeItem(newIncomeItem: IncomeItem, context: CoroutineContext) {
        val index = added.indexOfFirst { it.id == newIncomeItem.id }
        if (index >= 0) {
            added[index] = newIncomeItem
        }
    }
}

class FakeIdeaItemRepository : IdeaItemRepository {
    val added = mutableListOf<Idea>()

    override suspend fun addIdea(idea: Idea, context: CoroutineContext) {
        added += idea
    }

    override suspend fun editIdea(idea: Idea, context: CoroutineContext) = Unit

    override suspend fun deleteIdea(idea: Idea, context: CoroutineContext) = Unit

    override suspend fun updateIdea(idea: Idea) = Unit
}

class FakeIdeaListRepository : IdeaListRepository {
    var incomePlans: List<IncomePlans> = emptyList()
    var expenseLimits: List<ExpenseLimits> = emptyList()
    var savings: List<Savings> = emptyList()
    var ideaCompletedValueById: Map<Int, Float> = emptyMap()

    val currencyChanges = mutableListOf<Pair<Currency, Currency>>()

    override fun getIncomesPlansList(context: CoroutineContext): Flow<List<IncomePlans>> = flowOf(incomePlans)

    override fun getExpenseLimitsList(context: CoroutineContext): Flow<List<ExpenseLimits>> = flowOf(expenseLimits)

    override fun getSavingsList(context: CoroutineContext): Flow<List<Savings>> = flowOf(savings)

    override fun getIdeaCompletedValue(idea: Idea): Flow<Float> = flowOf(ideaCompletedValueById[idea.id] ?: 0f)

    override suspend fun changePreferableCurrenciesOnIdeas(
        newPreferableCurrency: Currency,
        previousPreferableCurrency: Currency
    ) {
        currencyChanges += newPreferableCurrency to previousPreferableCurrency
    }

    override suspend fun getCountOfIdeas(): Int = incomePlans.size + expenseLimits.size + savings.size
}

class FakeExpensesCategoriesListRepository : ExpensesCategoriesListRepository {
    val categories = mutableListOf<ExpenseCategory>()

    override fun getCategoriesList(): Flow<List<ExpenseCategory>> = flowOf(categories.toList())

    override suspend fun getCategoryById(id: Int): ExpenseCategory =
        categories.first { it.categoryId == id }

    override suspend fun getCategoriesByIds(listOfIds: List<Int>): List<ExpenseCategory> =
        categories.filter { it.categoryId in listOfIds }

    override suspend fun addCategory(category: ExpenseCategory, context: CoroutineContext) {
        categories += category
    }

    override suspend fun editCategory(category: ExpenseCategory, context: CoroutineContext) {
        val index = categories.indexOfFirst { it.categoryId == category.categoryId }
        if (index >= 0) {
            categories[index] = category
        }
    }

    override suspend fun deleteCategory(category: ExpenseCategory, context: CoroutineContext) {
        categories.removeAll { it.categoryId == category.categoryId }
    }
}

class FakeIncomesCategoriesListRepository : IncomesCategoriesListRepository {
    val categories = mutableListOf<IncomeCategory>()

    override fun getCategoriesList(): Flow<List<IncomeCategory>> = flowOf(categories.toList())

    override suspend fun getOtherCategoryId(): Int = -1

    override suspend fun addCategory(category: IncomeCategory, context: CoroutineContext) {
        categories += category
    }

    override suspend fun editCategory(category: IncomeCategory, context: CoroutineContext) {
        val index = categories.indexOfFirst { it.categoryId == category.categoryId }
        if (index >= 0) {
            categories[index] = category
        }
    }

    override suspend fun deleteCategory(category: IncomeCategory, context: CoroutineContext) {
        categories.removeAll { it.categoryId == category.categoryId }
    }
}

class FakeExpensesListRepository : ExpensesListRepository {
    var expenses: List<ExpenseItem> = emptyList()

    override fun getExpensesList(): Flow<List<ExpenseItem>> = flowOf(expenses)

    override fun getExpensesListInTimeSpanDateDesc(startOfSpan: Long, endOfSpan: Long): Flow<List<ExpenseItem>> =
        flowOf(expenses.filter { it.date in startOfSpan..endOfSpan }.sortedByDescending { it.date })

    override suspend fun getExpensesByCategoryInTimeSpan(
        startOfSpan: Long,
        endOfSpan: Long,
        category: ExpenseCategory
    ): List<ExpenseItem> =
        expenses.filter { it.date in startOfSpan..endOfSpan && it.categoryId == category.categoryId }

    override suspend fun getExpensesByIds(listOfIds: List<Int>): List<ExpenseItem> =
        expenses.filter { it.id in listOfIds }

    override fun getSortedExpensesListDateAsc(): Flow<List<ExpenseItem>> = flowOf(expenses.sortedBy { it.date })

    override fun getSortedExpensesListDateDesc(): Flow<List<ExpenseItem>> =
        flowOf(expenses.sortedByDescending { it.date })
}

class FakeIncomeListRepository : IncomeListRepository {
    var incomes: List<IncomeItem> = emptyList()

    override fun getIncomesList(): Flow<List<IncomeItem>> = flowOf(incomes)

    override fun getSortedIncomesListDateAsc(): Flow<List<IncomeItem>> = flowOf(incomes.sortedBy { it.date })

    override fun getSortedIncomesListDateDesc(): Flow<List<IncomeItem>> =
        flowOf(incomes.sortedByDescending { it.date })

    override fun getIncomesByCategoryInTimeSpan(
        startOfSpan: Long,
        endOfSpan: Long,
        category: IncomeCategory
    ): Flow<List<IncomeItem>> = flowOf(incomes.filter { it.date in startOfSpan..endOfSpan && it.categoryId == category.categoryId })

    override fun getIncomesInTimeSpanDateDesc(startOfSpan: Long, endOfSpan: Long): Flow<List<IncomeItem>> =
        flowOf(incomes.filter { it.date in startOfSpan..endOfSpan }.sortedByDescending { it.date })
}

class FakeIncomeCoreRepository : IncomeCoreRepository {
    var countInSpan: Int = 0
    var sumInSpan: Float = 0f
    var averageInSpan: Float = 0f
    var countInSpanByCategories: Int = 0
    var sumInSpanByCategories: Float = 0f

    override fun getSumOfIncomesInTimeSpan(startOfSpan: Instant, endOfSpan: Instant): Flow<Float> = flowOf(sumInSpan)

    override fun getSumOfIncomesInTimeSpanByCategoriesIds(
        startOfSpan: Instant,
        endOfSpan: Instant,
        categoriesIds: List<Int>
    ): Flow<Float> = flowOf(sumInSpanByCategories)

    override fun getCountOfIncomesInSpan(startDate: Instant, endDate: Instant): Flow<Int> = flowOf(countInSpan)

    override fun getCountOfIncomesInSpanByCategoriesIds(
        startDate: Instant,
        endDate: Instant,
        categoriesIds: List<Int>
    ): Flow<Int> = flowOf(countInSpanByCategories)

    override fun getAverageInTimeSpan(startDate: Instant, endDate: Instant): Flow<Float> = flowOf(averageInSpan)
}

class FakeExpensesCoreRepository : ExpensesCoreRepository {
    var countInSpan: Int = 0
    var sumInSpan: Float = 0f
    var averageInSpan: Float = 0f
    var countInSpanByCategories: Int = 0
    var sumInSpanByCategories: Float = 0f

    override fun getSumOfExpensesInTimeSpan(start: Long, end: Long): Flow<Float> = flowOf(sumInSpan)

    override fun getSumOfExpensesByCategoriesInTimeSpan(
        start: Instant,
        end: Instant,
        categoriesIds: List<Int>
    ): Flow<Float> = flowOf(sumInSpanByCategories)

    override fun getCurrentMonthSumOfExpense(): Flow<Float> = flowOf(sumInSpan)

    override fun getCurrentMonthSumOfExpensesByCategoriesId(listOfCategoriesId: List<Int>): Flow<Float> =
        flowOf(sumInSpanByCategories)

    override fun getCountOfExpensesInSpan(startDate: Instant, endDate: Instant): Flow<Int> = flowOf(countInSpan)

    override fun getCountOfExpensesInSpanByCategoriesIds(
        startDate: Instant,
        endDate: Instant,
        categoriesIds: List<Int>
    ): Flow<Int> = flowOf(countInSpanByCategories)

    override fun getAverageInTimeSpan(startDate: Instant, endDate: Instant): Flow<Float> = flowOf(averageInSpan)
}

class FakeCurrenciesPreferenceRepository(
    initialPreferableCurrency: Currency = testCurrency("USD", 1.0)
) : CurrenciesPreferenceRepository {
    private val preferableCurrency = MutableStateFlow(initialPreferableCurrency)
    private val firstAdditionalCurrency = MutableStateFlow<Currency?>(null)
    private val secondAdditionalCurrency = MutableStateFlow<Currency?>(null)
    private val thirdAdditionalCurrency = MutableStateFlow<Currency?>(null)
    private val fourthAdditionalCurrency = MutableStateFlow<Currency?>(null)

    override fun getCurrenciesPreferences(): Flow<CurrenciesPreference> =
        flowOf(
            CurrenciesPreference(
                preferableCurrency = preferableCurrency.value.ticker,
                firstAdditionalCurrency = firstAdditionalCurrency.value?.ticker,
                secondAdditionalCurrency = secondAdditionalCurrency.value?.ticker,
                thirdAdditionalCurrency = thirdAdditionalCurrency.value?.ticker,
                fourthAdditionalCurrency = fourthAdditionalCurrency.value?.ticker
            )
        )

    override fun getCurrenciesPreferenceConverted(): Flow<CurrenciesPreferenceConverted> =
        flowOf(
            CurrenciesPreferenceConverted(
                preferableCurrency = preferableCurrency.value,
                firstAdditionalCurrency = firstAdditionalCurrency.value,
                secondAdditionalCurrency = secondAdditionalCurrency.value,
                thirdAdditionalCurrency = thirdAdditionalCurrency.value,
                fourthAdditionalCurrency = fourthAdditionalCurrency.value
            )
        )

    override suspend fun setPreferableCurrency(currency: Currency) {
        preferableCurrency.value = currency
    }

    override suspend fun setFirstAdditionalCurrency(currency: Currency?) {
        firstAdditionalCurrency.value = currency
    }

    override suspend fun setSecondAdditionalCurrency(currency: Currency?) {
        secondAdditionalCurrency.value = currency
    }

    override suspend fun setThirdAdditionalCurrency(currency: Currency?) {
        thirdAdditionalCurrency.value = currency
    }

    override suspend fun setFourthAdditionalCurrency(currency: Currency?) {
        fourthAdditionalCurrency.value = currency
    }

    override fun getPreferableCurrency(): Flow<Currency> = preferableCurrency

    override fun getFirstAdditionalCurrency(): Flow<Currency?> = firstAdditionalCurrency

    override fun getSecondAdditionalCurrency(): Flow<Currency?> = secondAdditionalCurrency

    override fun getThirdAdditionalCurrency(): Flow<Currency?> = thirdAdditionalCurrency

    override fun getFourthAdditionalCurrency(): Flow<Currency?> = fourthAdditionalCurrency
}

class FakeCurrencyDao : CurrencyDao {
    val currenciesByTicker = mutableMapOf<String, Currency>()

    override suspend fun insert(currency: Currency) {
        currenciesByTicker[currency.ticker] = currency
    }

    override suspend fun update(currency: Currency) {
        currenciesByTicker[currency.ticker] = currency
    }

    override suspend fun delete(currency: Currency) {
        currenciesByTicker.remove(currency.ticker)
    }

    override suspend fun updateRate(rate: Double, currencyTicker: String) {
        val current = currenciesByTicker[currencyTicker] ?: return
        currenciesByTicker[currencyTicker] = current.copy(rate = rate)
    }

    override suspend fun getCurrencyByTicker(currencyTicker: String): Currency =
        currenciesByTicker.getValue(currencyTicker)

    override fun getAllData(): Flow<List<Currency>> = flowOf(currenciesByTicker.values.toList())
}

class InMemoryPreferencesDataStore(
    initialPreferences: Preferences = emptyPreferences()
) : DataStore<Preferences> {
    private val state = MutableStateFlow(initialPreferences)

    override val data: Flow<Preferences> = state

    override suspend fun updateData(transform: suspend (t: Preferences) -> Preferences): Preferences {
        val updated = transform(state.value)
        state.value = updated
        return updated
    }
}

class FakeExpenseItemsDao : ExpenseItemsDAO {
    var allExpenses: List<ExpenseItem> = emptyList()

    override suspend fun insertItem(expenseItem: ExpenseItem) {
        allExpenses = allExpenses + expenseItem
    }

    override suspend fun update(expenseItem: ExpenseItem) {
        allExpenses = allExpenses.map {
            if (it.id == expenseItem.id) {
                expenseItem
            } else {
                it
            }
        }
    }

    override suspend fun deleteItem(expenseItem: ExpenseItem) {
        allExpenses = allExpenses.filterNot { it.id == expenseItem.id }
    }

    override fun getAll(): Flow<List<ExpenseItem>> = flowOf(allExpenses)

    override fun getAllWithDateDesc(): Flow<List<ExpenseItem>> = flowOf(allExpenses.sortedByDescending { it.date })

    override fun getAllWithDateAsc(): Flow<List<ExpenseItem>> = flowOf(allExpenses.sortedBy { it.date })

    override suspend fun getCategoriesByExpenseItemIds(listOfIds: List<Int>): List<ExpenseCategory> = emptyList()

    override fun getExpensesInTimeSpanDateDesc(start: Long, end: Long): Flow<List<ExpenseItem>> =
        flowOf(allExpenses.filter { it.date in start..end }.sortedByDescending { it.date })

    override fun getExpensesInTimeSpanDateAsc(start: Long, end: Long): Flow<List<ExpenseItem>> =
        flowOf(allExpenses.filter { it.date in start..end }.sortedBy { it.date })

    override fun getCountOfExpensesInTimeSpan(start: Long, end: Long): Flow<Int> =
        flowOf(allExpenses.count { it.date in start..end })

    override fun getCountOfExpensesInTimeSpanByCategoriesIds(
        start: Long,
        end: Long,
        categoriesIds: List<Int>
    ): Flow<Int> = flowOf(allExpenses.count { it.date in start..end && it.categoryId in categoriesIds })

    override suspend fun getBiggestExpenseInTimeSpan(start: Long, end: Long): Float? =
        allExpenses.filter { it.date in start..end }.maxOfOrNull { it.value }

    override fun getExpensesByCategoriesIdInTimeSpan(
        start: Long,
        end: Long,
        listOfCategoriesId: List<Int>
    ): Flow<List<ExpenseItem>> = flowOf(allExpenses.filter { it.date in start..end && it.categoryId in listOfCategoriesId })

    override suspend fun findExpenseById(id: Int): ExpenseItem? = allExpenses.find { it.id == id }

    override fun findExpenseByNotes(note: String): Flow<List<ExpenseItem>> =
        flowOf(allExpenses.filter { it.note.contains(note) })

    override suspend fun findExpensesInTimeSpan(start: Long, end: Long, categoryId: Int): List<ExpenseItem> =
        allExpenses.filter { it.date in start..end && it.categoryId == categoryId }

    override suspend fun getExpensesByIds(listOfIds: List<Int>): List<ExpenseItem> =
        allExpenses.filter { it.id in listOfIds }
}
