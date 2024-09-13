package com.savenko.track.presentation.screens.states.core.mainScreen

import com.savenko.track.domain.models.abstractLayer.FinancialEntity
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.expenses.ExpenseItem
import com.savenko.track.domain.models.incomes.IncomeCategory
import com.savenko.track.domain.models.incomes.IncomeItem

/**
 * Financial lazy column state
 *
 * @property isExpenseLazyColumn If true, lazy column shows *expense* financials
 * @property expensesList list of user expense financials
 * @property expenseCategoriesList list of user expense categories
 * @property incomeList list of user income financials
 * @property incomeCategoriesList list of user income categories
 * @property currenciesList list of all Track currencies
 * @property expensesFinancialSummary map of expenses financials ids to its [FinancialCardNotion](com.savenko.track.presentation.screens.states.core.mainScreen.FinancialCardNotion)
 * @property incomesFinancialSummary map of incomes financials ids to its [FinancialCardNotion](com.savenko.track.presentation.screens.states.core.mainScreen.FinancialCardNotion)
 * @property preferableCurrency user preferable currency
 * @property isScrolledBelow handles visibility of [TrackInfoCards](com.savenko.track.presentation.screens.screenComponents.mainScreenRelated.mainScreenInfoCards.TrackScreenInfoCards) if needed
 * @property expandedFinancialEntity id of expanded financial entity
 */
data class FinancialLazyColumnState(
    val isExpenseLazyColumn: Boolean,
    val expensesList: List<ExpenseItem> = listOf(),
    val expenseCategoriesList: List<ExpenseCategory> = listOf(),
    val incomeList: List<IncomeItem> = listOf(),
    val incomeCategoriesList: List<IncomeCategory> = listOf(),
    val currenciesList : List<Currency>,
    val expensesFinancialSummary : Map<Int, FinancialCardNotion>,
    val incomesFinancialSummary : Map<Int, FinancialCardNotion>,
    val preferableCurrency : Currency,
    val isScrolledBelow: Boolean,
    val expandedFinancialEntity: FinancialEntity?
)