package com.savenko.track.data.viewmodels.mainScreen.lazyColumn

import android.util.Range
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.data.core.FinancialCardNotesProvider
import com.savenko.track.data.implementations.expenses.expenseCategories.ExpensesCategoriesListRepositoryImpl
import com.savenko.track.data.implementations.incomes.incomeCategories.IncomesCategoriesListRepositoryImpl
import com.savenko.track.data.other.constants.FIRST_VISIBLE_INDEX_FEED_DISSAPEARANCE
import com.savenko.track.data.other.converters.dates.getEndOfTheMonth
import com.savenko.track.data.other.converters.dates.getStartOfMonthDate
import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.domain.models.abstractLayer.FinancialEntity
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.expenses.ExpenseItem
import com.savenko.track.domain.models.incomes.IncomeCategory
import com.savenko.track.domain.models.incomes.IncomeItem
import com.savenko.track.domain.usecases.crud.financials.DeleteFinancialItemUseCase
import com.savenko.track.domain.usecases.userData.financialEntities.nonSpecified.GetUserExpensesUseCase
import com.savenko.track.domain.usecases.userData.financialEntities.nonSpecified.GetUserIncomesUseCase
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
    private val categoriesListRepositoryImpl: ExpensesCategoriesListRepositoryImpl,
    private val incomesCategoriesListRepositoryImpl: IncomesCategoriesListRepositoryImpl,
    private val financialCardNotesProvider: FinancialCardNotesProvider
) : ViewModel() {
    private val _expensesList = mutableStateListOf<ExpenseItem>()
    val expensesList: List<ExpenseItem> = _expensesList
    private val _expenseCategoriesList = mutableStateListOf<ExpenseCategory>()
    val expenseCategoriesList: List<ExpenseCategory> = _expenseCategoriesList
    private val _incomeList = mutableStateListOf<IncomeItem>()
    val incomeList: List<IncomeItem> = _incomeList
    private val _incomeCategoriesList = mutableStateListOf<IncomeCategory>()
    val incomeCategoriesList: List<IncomeCategory> = _incomeCategoriesList

    private val _isScrolledBelow = MutableStateFlow(value = false)
    val isScrolledBelow = _isScrolledBelow.asStateFlow()

    private val _expandedFinancialEntity: MutableStateFlow<FinancialEntity?> = MutableStateFlow(value = null)
    val expandedFinancialEntity = _expandedFinancialEntity.asStateFlow()

    private val _isExpenseLazyColumn = MutableStateFlow(value = true)
    val isExpenseLazyColumn = _isExpenseLazyColumn.asStateFlow()

    init {
        viewModelScope.launch {
            categoriesListRepositoryImpl.getCategoriesList().collect {
                _expenseCategoriesList.clear()
                _expenseCategoriesList.addAll(it)
            }
        }
        viewModelScope.launch {
            getUserExpensesUseCase().collect {
                _expensesList.clear()
                _expensesList.addAll(it)
            }
        }
        viewModelScope.launch {
            getUserIncomesUseCase().collect {
                _incomeList.clear()
                _incomeList.addAll(it)
            }
        }
        viewModelScope.launch {
            incomesCategoriesListRepositoryImpl.getCategoriesList().collect {
                _incomeCategoriesList.clear()
                _incomeCategoriesList.addAll(it)
            }
        }
    }

    fun setExpandedExpenseCard(value: FinancialEntity?) {
        _expandedFinancialEntity.update { value }
    }

    fun setScrolledBelow(firstVisibleIndex: Int) {
        if (isExpenseLazyColumn.value) {
            _isScrolledBelow.update { firstVisibleIndex != 0 && _expensesList.size > FIRST_VISIBLE_INDEX_FEED_DISSAPEARANCE }
        } else {
            _isScrolledBelow.update { firstVisibleIndex != 0 && _incomeList.size > FIRST_VISIBLE_INDEX_FEED_DISSAPEARANCE }
        }
    }

    fun toggleIsExpenseLazyColumn() {
        _isExpenseLazyColumn.value = !_isExpenseLazyColumn.value
    }

    suspend fun deleteFinancialItem(financialEntity: FinancialEntity){
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

