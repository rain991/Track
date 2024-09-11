package com.savenko.track.data.viewmodels.statistics

import android.util.Range
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.data.core.FinancialCardNotesProvider
import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.domain.models.abstractLayer.FinancialEntities
import com.savenko.track.domain.models.abstractLayer.FinancialEntity
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.incomes.IncomeCategory
import com.savenko.track.domain.repository.expenses.categories.ExpensesCategoriesListRepository
import com.savenko.track.domain.repository.incomes.categories.IncomesCategoriesListRepository
import com.savenko.track.domain.usecases.userData.financialEntities.specified.GetDesiredExpensesUseCase
import com.savenko.track.domain.usecases.userData.financialEntities.specified.GetDesiredFinancialEntitiesUseCase
import com.savenko.track.domain.usecases.userData.financialEntities.specified.GetDesiredIncomesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Date

/**
 * Provides data for [TrackStatisticLazyColumn](com.savenko.track.presentation.screens.screenComponents.statisticsScreenRelated.components.TrackStatisticLazyColumn)
 */
class StatisticLazyColumnViewModel(
    private val getDesiredIncomesUseCase: GetDesiredIncomesUseCase,
    private val getDesiredExpensesUseCase: GetDesiredExpensesUseCase,
    private val getDesiredFinancialEntitiesUseCase: GetDesiredFinancialEntitiesUseCase,
    private val incomesCategoriesListRepositoryImpl: IncomesCategoriesListRepository,
    private val expensesCategoriesListRepository: ExpensesCategoriesListRepository,
    private val financialCardNotesProvider: FinancialCardNotesProvider
) : ViewModel() {
    private val _listOfFilteredFinancialEntities = mutableStateListOf<FinancialEntity>()
    val listOfFilteredFinancialEntities: List<FinancialEntity> = _listOfFilteredFinancialEntities

    private val _expenseCategoriesList = mutableStateListOf<ExpenseCategory>()
    val expenseCategoriesList: List<ExpenseCategory> = _expenseCategoriesList

    private val _incomeCategoriesList = mutableStateListOf<IncomeCategory>()
    val incomeCategoriesList: List<IncomeCategory> = _incomeCategoriesList

    suspend fun initializeListOfEntities(
        timePeriod: Range<Date>,
        financialEntities: FinancialEntities
    ) {
        viewModelScope.launch {
            launch {
                when (financialEntities) {
                    is FinancialEntities.IncomeFinancialEntity -> {
                        getDesiredIncomesUseCase(timePeriod.lower.time, timePeriod.upper.time).collect {
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
                        getDesiredFinancialEntitiesUseCase(
                            timePeriod.lower.time,
                            timePeriod.upper.time
                        ).collect { listOfFinancialEntities ->
                            _listOfFilteredFinancialEntities.clear()
                            _listOfFilteredFinancialEntities.addAll(listOfFinancialEntities.sortedByDescending { it.date.time })
                        }
                    }
                }
            }
            launch {
                incomesCategoriesListRepositoryImpl.getCategoriesList().collect {
                    _incomeCategoriesList.clear()
                    _incomeCategoriesList.addAll(it)
                }
            }
            launch {
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