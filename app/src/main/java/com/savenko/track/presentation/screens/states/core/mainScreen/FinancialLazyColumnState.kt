package com.savenko.track.presentation.screens.states.core.mainScreen

import com.savenko.track.domain.models.abstractLayer.FinancialEntity
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.expenses.ExpenseItem
import com.savenko.track.domain.models.incomes.IncomeCategory
import com.savenko.track.domain.models.incomes.IncomeItem

data class FinancialLazyColumnState(
    val expensesList: List<ExpenseItem> = listOf(),
    val expenseCategoriesList: List<ExpenseCategory> = listOf(),
    val incomeList: List<IncomeItem> = listOf(),
    val incomeCategoriesList: List<IncomeCategory> = listOf(),
    val currenciesList : List<Currency>,
    val expensesFinancialSummary : Map<Int, FinancialCardNotion>,
    val incomesFinancialSummary : Map<Int, FinancialCardNotion>,
    val preferableCurrency : Currency,
    val isScrolledBelow: Boolean,
    val expandedFinancialEntity: FinancialEntity?,
    val isExpenseLazyColumn: Boolean
)