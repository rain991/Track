package com.example.track.data.viewmodels.statistics

import android.util.Range
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.track.data.core.FinancialCardNotesProvider
import com.example.track.data.implementations.expenses.ExpensesListRepositoryImpl
import com.example.track.data.implementations.expenses.categories.ExpensesCategoriesListRepositoryImpl
import com.example.track.data.implementations.incomes.IncomeListRepositoryImpl
import com.example.track.data.implementations.incomes.categories.IncomesCategoriesListRepositoryImpl
import com.example.track.domain.models.abstractLayer.CategoryEntity
import com.example.track.domain.models.abstractLayer.FinancialEntities
import com.example.track.domain.models.abstractLayer.FinancialEntity
import com.example.track.domain.models.expenses.ExpenseCategory
import com.example.track.domain.models.incomes.IncomeCategory
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.Date

class StatisticLazyColumnViewModel(
    private val incomeListRepositoryImpl: IncomeListRepositoryImpl,
    private val expensesListRepositoryImpl: ExpensesListRepositoryImpl,
    private val incomesCategoriesListRepositoryImpl: IncomesCategoriesListRepositoryImpl,
    private val expensesCategoriesListRepository: ExpensesCategoriesListRepositoryImpl,
    private val financialCardNotesProvider: FinancialCardNotesProvider
) : ViewModel() {
    private val _listOfFilteredFinancialEntities = mutableStateListOf<FinancialEntity>()
    val listOfFilteredFinancialEntities: List<FinancialEntity> = _listOfFilteredFinancialEntities

    private val _expenseCategoriesList = mutableStateListOf<ExpenseCategory>()
    val expenseCategoriesList: List<ExpenseCategory> = _expenseCategoriesList

    private val _incomeCategoriesList = mutableStateListOf<IncomeCategory>()
    val incomeCategoriesList: List<IncomeCategory> = _incomeCategoriesList

    suspend fun innitializeListOfEntities(timePeriod: Range<Date>, financialEntities: FinancialEntities) {
        viewModelScope.launch {
            async {
                when (financialEntities) {
                    is FinancialEntities.IncomeFinancialEntity -> {
                        incomeListRepositoryImpl.getIncomesInTimeSpanDateDesc(timePeriod.lower, timePeriod.upper).collect {
                            _listOfFilteredFinancialEntities.clear()
                            _listOfFilteredFinancialEntities.addAll(it)
                        }
                    }

                    is FinancialEntities.ExpenseFinancialEntity -> {
                        expensesListRepositoryImpl.getExpensesListInTimeSpanDateDesc(timePeriod.lower, timePeriod.upper).collect {
                            _listOfFilteredFinancialEntities.clear()
                            _listOfFilteredFinancialEntities.addAll(it)

                        }
                    }

                    is FinancialEntities.Both -> {
                        val expenseItemsFlow =
                            expensesListRepositoryImpl.getExpensesListInTimeSpanDateDesc(timePeriod.lower, timePeriod.upper)
                        val incomeItemsFlow = incomeListRepositoryImpl.getIncomesInTimeSpanDateDesc(timePeriod.lower, timePeriod.upper)
                        combine(expenseItemsFlow, incomeItemsFlow) { expenseItems, incomeItems ->
                            val list = mutableListOf<FinancialEntity>()
                            list.addAll(expenseItems)
                            list.addAll(incomeItems)
                            list
                        }.collect { listOfFinancialEntities ->
                            _listOfFilteredFinancialEntities.clear()
                            _listOfFilteredFinancialEntities.addAll(listOfFinancialEntities.sortedByDescending { it.date.time })
                        }
                    }
                }
            }
            async {
                incomesCategoriesListRepositoryImpl.getCategoriesList().collect {
                    _incomeCategoriesList.clear()
                    _incomeCategoriesList.addAll(it)
                }
            }
            async {
                expensesCategoriesListRepository.getCategoriesList().collect {
                    _expenseCategoriesList.clear()
                    _expenseCategoriesList.addAll(it)
                }
            }
        }
    }

    suspend fun requestCountInDateRangeNotion(
        financialEntity: FinancialEntity,
        financialCategory: CategoryEntity,
        dateRange: Range<Date>
    ): Flow<Int> {
        return financialCardNotesProvider.requestCountNotionForFinancialCard(financialEntity, financialCategory, dateRange)
    }

    suspend fun requestSummaryInDateRangeNotion(
        financialEntity: FinancialEntity,
        financialCategory: CategoryEntity,
        dateRange: Range<Date>
    ): Flow<Float> {
        return financialCardNotesProvider.requestValueSummaryNotionForFinancialCard(financialEntity, financialCategory, dateRange)
    }
}