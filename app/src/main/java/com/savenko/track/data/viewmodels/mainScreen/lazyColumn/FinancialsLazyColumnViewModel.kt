package com.savenko.track.data.viewmodels.mainScreen.lazyColumn

import android.util.Range
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.data.core.FinancialCardNotesProvider
import com.savenko.track.data.other.constants.FIRST_VISIBLE_INDEX_FEED_DISSAPEARANCE
import com.savenko.track.data.other.converters.dates.getEndOfTheMonth
import com.savenko.track.data.other.converters.dates.getStartOfMonthDate
import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.domain.models.abstractLayer.FinancialEntity
import com.savenko.track.domain.repository.currencies.CurrencyListRepository
import com.savenko.track.domain.repository.expenses.categories.ExpensesCategoriesListRepository
import com.savenko.track.domain.repository.incomes.categories.IncomesCategoriesListRepository
import com.savenko.track.domain.usecases.crud.financials.DeleteFinancialItemUseCase
import com.savenko.track.domain.usecases.userData.financialEntities.nonSpecified.GetUserExpensesUseCase
import com.savenko.track.domain.usecases.userData.financialEntities.nonSpecified.GetUserIncomesUseCase
import com.savenko.track.presentation.screens.states.core.mainScreen.FinancialLazyColumnState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

/* This VM contains needed components for UI of expenses lazyColumn on mainList, also data for TrackScreenInfoComposable */
class FinancialsLazyColumnViewModel(
    private val getUserExpensesUseCase: GetUserExpensesUseCase,
    private val getUserIncomesUseCase: GetUserIncomesUseCase,
    private val deleteFinancialItemUseCase: DeleteFinancialItemUseCase,
    private val expensesCategoriesListRepositoryImpl: ExpensesCategoriesListRepository,
    private val incomesCategoriesListRepositoryImpl: IncomesCategoriesListRepository,
    private val currencyListRepositoryImpl : CurrencyListRepository,
    private val financialCardNotesProvider: FinancialCardNotesProvider
) : ViewModel() {
    private val _financialLazyColumnState =
        MutableStateFlow(
            FinancialLazyColumnState(
                currenciesList = listOf(),
                isScrolledBelow = false,
                expandedFinancialEntity = null,
                isExpenseLazyColumn = true
            )
        )
    val financialLazyColumnState = _financialLazyColumnState.asStateFlow()

    init {
        viewModelScope.launch {
            expensesCategoriesListRepositoryImpl.getCategoriesList().collect { listOfExpenseCategories ->
                _financialLazyColumnState.update { _financialLazyColumnState.value.copy(expenseCategoriesList = listOfExpenseCategories) }
            }
        }
        viewModelScope.launch {
            getUserExpensesUseCase().collect { listOfExpenses ->
                _financialLazyColumnState.update { _financialLazyColumnState.value.copy(expensesList = listOfExpenses) }
            }
        }
        viewModelScope.launch {
            getUserIncomesUseCase().collect { incomeList ->
                _financialLazyColumnState.update { _financialLazyColumnState.value.copy(incomeList = incomeList) }
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
        //_isExpenseLazyColumn.value = !_isExpenseLazyColumn.value
    }

    suspend fun deleteFinancialItem(financialEntity: FinancialEntity) {
        deleteFinancialItemUseCase(financialEntity)
    }


    suspend fun requestCountInMonthNotion(
        financialEntity: FinancialEntity,
        financialCategory: CategoryEntity
    ): Flow<Int> {
        val currentMonthDateRange = Range(
            getStartOfMonthDate(Date(financialEntity.date.time)),
            getEndOfTheMonth(Date(System.currentTimeMillis()))
        )
        return financialCardNotesProvider.requestCountNotionForFinancialCard(
            financialEntity,
            financialCategory,
            currentMonthDateRange
        )
    }

    suspend fun requestSummaryInMonthNotion(
        financialEntity: FinancialEntity,
        financialCategory: CategoryEntity
    ): Flow<Float> {
        val currentMonthDateRange = Range(
            getStartOfMonthDate(Date(financialEntity.date.time)),
            getEndOfTheMonth(Date(System.currentTimeMillis()))
        )
        return financialCardNotesProvider.requestValueSummaryNotionForFinancialCard(
            financialEntity,
            financialCategory,
            currentMonthDateRange
        )
    }
}

