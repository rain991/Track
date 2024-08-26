package com.savenko.track.data.viewmodels.mainScreen.lazyColumn

import android.util.Range
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.data.other.constants.CURRENCY_DEFAULT
import com.savenko.track.data.other.constants.FIRST_VISIBLE_INDEX_FEED_DISSAPEARANCE
import com.savenko.track.data.other.converters.dates.getEndOfMonthDate
import com.savenko.track.data.other.converters.dates.getStartOfMonthDate
import com.savenko.track.domain.models.abstractLayer.FinancialEntity
import com.savenko.track.domain.models.abstractLayer.FinancialTypes
import com.savenko.track.domain.models.expenses.ExpenseItem
import com.savenko.track.domain.models.incomes.IncomeItem
import com.savenko.track.domain.repository.currencies.CurrenciesPreferenceRepository
import com.savenko.track.domain.repository.currencies.CurrencyListRepository
import com.savenko.track.domain.repository.expenses.categories.ExpensesCategoriesListRepository
import com.savenko.track.domain.repository.incomes.categories.IncomesCategoriesListRepository
import com.savenko.track.domain.usecases.crud.financials.DeleteFinancialItemUseCase
import com.savenko.track.domain.usecases.userData.financialEntities.nonSpecified.GetUserExpensesUseCase
import com.savenko.track.domain.usecases.userData.financialEntities.nonSpecified.GetUserIncomesUseCase
import com.savenko.track.domain.usecases.userData.financialEntities.specified.GetPeriodSummaryUseCase
import com.savenko.track.presentation.screens.states.core.mainScreen.FinancialCardNotion
import com.savenko.track.presentation.screens.states.core.mainScreen.FinancialLazyColumnState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

/* This VM contains needed components for UI of expenses lazyColumn on mainList, also data for TrackScreenInfoComposable */
class FinancialsLazyColumnViewModel(
    private val getUserExpensesUseCase: GetUserExpensesUseCase,
    private val getUserIncomesUseCase: GetUserIncomesUseCase,
    private val getPeriodSummaryUseCase: GetPeriodSummaryUseCase,
    private val deleteFinancialItemUseCase: DeleteFinancialItemUseCase,
    private val expensesCategoriesListRepositoryImpl: ExpensesCategoriesListRepository,
    private val incomesCategoriesListRepositoryImpl: IncomesCategoriesListRepository,
    private val currencyListRepositoryImpl: CurrencyListRepository,
    private val currenciesPreferenceRepository: CurrenciesPreferenceRepository
) : ViewModel() {
    private val _financialLazyColumnState =
        MutableStateFlow(
            FinancialLazyColumnState(
                currenciesList = listOf(),
                isScrolledBelow = false,
                expandedFinancialEntity = null,
                isExpenseLazyColumn = true,
                expensesFinancialSummary = mapOf(),
                incomesFinancialSummary = mapOf(),
                preferableCurrency = CURRENCY_DEFAULT
            )
        )
    val financialLazyColumnState = _financialLazyColumnState.asStateFlow()

    init {
        viewModelScope.launch {
            getUserExpensesUseCase.getUserExpensesDateDesc().collect { listOfExpenses ->
                _financialLazyColumnState.update { _financialLazyColumnState.value.copy(expensesList = listOfExpenses) }
                initializeExpenseListFinancialNotions(listOfExpenses)
            }
        }
        viewModelScope.launch {
            getUserIncomesUseCase.getUserIncomesDateDesc().collect { listOfIncomes ->
                _financialLazyColumnState.update { _financialLazyColumnState.value.copy(incomeList = listOfIncomes) }
                initializeIncomeListFinancialNotions(listOfIncomes)
            }
        }

        viewModelScope.launch {
            expensesCategoriesListRepositoryImpl.getCategoriesList().collect { listOfExpenseCategories ->
                _financialLazyColumnState.update { _financialLazyColumnState.value.copy(expenseCategoriesList = listOfExpenseCategories) }
            }
        }

        viewModelScope.launch {
            incomesCategoriesListRepositoryImpl.getCategoriesList().collect { incomeCategoriesList ->
                _financialLazyColumnState.update { _financialLazyColumnState.value.copy(incomeCategoriesList = incomeCategoriesList) }
            }
        }
        viewModelScope.launch {
            currencyListRepositoryImpl.getCurrencyList().collect { currenciesList ->
                _financialLazyColumnState.update { _financialLazyColumnState.value.copy(currenciesList = currenciesList) }
            }
        }
        viewModelScope.launch {
            currenciesPreferenceRepository.getPreferableCurrency().collect { preferableCurrency ->
                _financialLazyColumnState.update { _financialLazyColumnState.value.copy(preferableCurrency = preferableCurrency) }
            }
        }
    }

    fun setExpandedExpenseCard(value: FinancialEntity?) {
        _financialLazyColumnState.update { _financialLazyColumnState.value.copy(expandedFinancialEntity = value) }
    }

    fun setScrolledBelow(firstVisibleIndex: Int) {
        if (_financialLazyColumnState.value.isExpenseLazyColumn) {
            _financialLazyColumnState.update { _financialLazyColumnState.value.copy(isScrolledBelow = firstVisibleIndex != 0 && _financialLazyColumnState.value.expensesList.size > FIRST_VISIBLE_INDEX_FEED_DISSAPEARANCE) }
        } else {
            _financialLazyColumnState.update { _financialLazyColumnState.value.copy(isScrolledBelow = firstVisibleIndex != 0 && _financialLazyColumnState.value.incomeList.size > FIRST_VISIBLE_INDEX_FEED_DISSAPEARANCE) }
        }
    }

    fun toggleIsExpenseLazyColumn() {
        _financialLazyColumnState.update { _financialLazyColumnState.value.copy(isExpenseLazyColumn = !_financialLazyColumnState.value.isExpenseLazyColumn) }
    }

    suspend fun deleteFinancialItem(financialEntity: FinancialEntity) {
        deleteFinancialItemUseCase(financialEntity)
    }

    private suspend fun initializeExpenseListFinancialNotions(listOfExpenses: List<ExpenseItem>) {
        val map = listOfExpenses.associate { expenseItem ->
            expenseItem.id to getPeriodSummaryUseCase.getPeriodSummaryById(
                periodStartMillis = getStartOfMonthDate(expenseItem.date).time,
                periodEndMillis = getEndOfMonthDate(expenseItem.date).time,
                financialTypes = FinancialTypes.Expense,
                categoryID = expenseItem.categoryId
            ).first()
        }
        _financialLazyColumnState.update { _financialLazyColumnState.value.copy(expensesFinancialSummary = map) }
    }

    private suspend fun initializeIncomeListFinancialNotions(listOfIncomes: List<IncomeItem>) {
        val map = listOfIncomes.associate { incomeItem ->
            incomeItem.id to getPeriodSummaryUseCase.getPeriodSummaryById(
                periodStartMillis = getStartOfMonthDate(incomeItem.date).time,
                periodEndMillis = getEndOfMonthDate(incomeItem.date).time,
                financialTypes = FinancialTypes.Income,
                categoryID = incomeItem.categoryId
            ).first()
        }
        _financialLazyColumnState.update { _financialLazyColumnState.value.copy(incomesFinancialSummary = map) }
    }

    suspend fun requestMonthSummary(monthDate: Date): FinancialCardNotion {
        val dateRange = Range(getStartOfMonthDate(monthDate), getEndOfMonthDate(monthDate))
        return if (_financialLazyColumnState.value.isExpenseLazyColumn) {
            getPeriodSummaryUseCase.getPeriodSummary(
                periodStartMillis = dateRange.lower.time,
                periodEndMillis = dateRange.upper.time,
                financialTypes = FinancialTypes.Expense
            ).first()
        } else {
            getPeriodSummaryUseCase.getPeriodSummary(
                periodStartMillis = dateRange.lower.time,
                periodEndMillis = dateRange.upper.time,
                financialTypes = FinancialTypes.Income
            ).first()
        }
    }
}

