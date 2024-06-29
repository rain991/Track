package com.savenko.track.data.viewmodels.statistics

import android.util.Range
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.data.core.FinancialCardNotesProvider
import com.savenko.track.data.implementations.expenses.categories.ExpensesCategoriesListRepositoryImpl
import com.savenko.track.data.implementations.incomes.categories.IncomesCategoriesListRepositoryImpl
import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.domain.models.abstractLayer.FinancialEntities
import com.savenko.track.domain.models.abstractLayer.FinancialEntity
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.incomes.IncomeCategory
import com.savenko.track.domain.usecases.userData.financialEntities.specified.GetDesiredExpensesUseCase
import com.savenko.track.domain.usecases.userData.financialEntities.specified.GetDesiredFinancialEntitiesUseCase
import com.savenko.track.domain.usecases.userData.financialEntities.specified.GetDesiredIncomesUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Date

class StatisticLazyColumnViewModel(
    private val getDesiredIncomesUseCase: GetDesiredIncomesUseCase,
    private val getDesiredExpensesUseCase: GetDesiredExpensesUseCase,
    private val getDesiredFinancialEntitiesUseCase: GetDesiredFinancialEntitiesUseCase,
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

    suspend fun innitializeListOfEntities(
        timePeriod: Range<Date>,
        financialEntities: FinancialEntities
    ) {
        viewModelScope.launch {
            async {
                when (financialEntities) {
                    is FinancialEntities.IncomeFinancialEntity -> {
                        getDesiredIncomesUseCase(timePeriod).collect {
                            _listOfFilteredFinancialEntities.clear()
                            _listOfFilteredFinancialEntities.addAll(it)
                        }
                    }

                    is FinancialEntities.ExpenseFinancialEntity -> {
                        getDesiredExpensesUseCase(timePeriod).collect {
                            _listOfFilteredFinancialEntities.clear()
                            _listOfFilteredFinancialEntities.addAll(it)

                        }
                    }

                    is FinancialEntities.Both -> {
                        getDesiredFinancialEntitiesUseCase(timePeriod).collect { listOfFinancialEntities ->
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
        return financialCardNotesProvider.requestCountNotionForFinancialCard(
            financialEntity,
            financialCategory,
            dateRange
        )
    }

    suspend fun requestSummaryInDateRangeNotion(
        financialEntity: FinancialEntity,
        financialCategory: CategoryEntity,
        dateRange: Range<Date>
    ): Flow<Float> {
        return financialCardNotesProvider.requestValueSummaryNotionForFinancialCard(
            financialEntity,
            financialCategory,
            dateRange
        )
    }
}