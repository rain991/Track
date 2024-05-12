package com.example.track.data.viewmodels.mainScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.track.data.other.constants.FIRST_VISIBLE_INDEX_FEED_DISSAPEARANCE
import com.example.track.data.other.converters.getEndOfTheMonth
import com.example.track.data.other.converters.getStartOfMonthDate
import com.example.track.data.implementations.expenses.ExpensesCategoriesListRepositoryImpl
import com.example.track.data.implementations.expenses.ExpensesListRepositoryImpl
import com.example.track.data.implementations.incomes.IncomeListRepositoryImpl
import com.example.track.data.implementations.incomes.IncomesCategoriesListRepositoryImpl
import com.example.track.domain.models.Expenses.ExpenseCategory
import com.example.track.domain.models.Expenses.ExpenseItem
import com.example.track.domain.models.incomes.IncomeCategory
import com.example.track.domain.models.incomes.IncomeItem
import com.example.track.domain.models.other.CategoryEntity
import com.example.track.domain.models.other.FinancialEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExpenseAndIncomeLazyColumnViewModel(
    private val expensesListRepositoryImpl: ExpensesListRepositoryImpl,
    private val categoriesListRepositoryImpl: ExpensesCategoriesListRepositoryImpl,
    private val incomeListRepositoryImpl: IncomeListRepositoryImpl,
    private val incomesCategoriesListRepositoryImpl: IncomesCategoriesListRepositoryImpl
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
            expensesListRepositoryImpl.getSortedExpensesListDateDesc().collect {
                _expensesList.clear()
                _expensesList.addAll(it)
            }
        }
        viewModelScope.launch {
            incomeListRepositoryImpl.getSortedIncomesListDateDesc().collect {
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

    suspend fun requestCountInMonthNotion(financialEntity: FinancialEntity, financialCategory: CategoryEntity): String {
        val startDate = getStartOfMonthDate(financialEntity.date)
        val endDate = getEndOfTheMonth(financialEntity.date)
        if (financialEntity is ExpenseItem && financialCategory is ExpenseCategory) {
            val result = expensesListRepositoryImpl.getExpensesByCategoryInTimeSpan(
                startOfSpan = startDate,
                endOfSpan = endDate,
                category = financialCategory
            )
            return "${result.size}"
        } else if (financialEntity is IncomeItem && financialCategory is IncomeCategory) {
            val result = incomeListRepositoryImpl.getIncomesByCategoryInTimeSpan(
                startOfSpan = startDate,
                endOfSpan = endDate,
                category = financialCategory
            )
            return "${result.size}"
        } else {
            return ""
        }
    }

    suspend fun requestSumExpensesInMonthNotion(financialEntity: FinancialEntity, financialCategory: CategoryEntity): String {
        val startDate= getStartOfMonthDate(financialEntity.date)
        val endDate = getEndOfTheMonth(financialEntity.date)

        if (financialEntity is ExpenseItem && financialCategory is ExpenseCategory) {
            val result = expensesListRepositoryImpl.getExpensesByCategoryInTimeSpan(
                startOfSpan = startDate,
                endOfSpan = endDate,
                category = financialCategory
            )
            val sum = result.sumOf { it.value.toInt() } // warning Crypto
            return "$sum"
        } else if (financialEntity is IncomeItem && financialCategory is IncomeCategory) {
            val result = incomeListRepositoryImpl.getIncomesByCategoryInTimeSpan(
                startOfSpan = startDate,
                endOfSpan = endDate,
                category = financialCategory
            )
            val sum = result.sumOf { it.value.toInt() } // warning Crypto
            return "$sum"
        } else {
            return ""
        }
    }
}