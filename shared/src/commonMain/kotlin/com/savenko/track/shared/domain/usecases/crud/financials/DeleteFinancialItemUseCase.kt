package com.savenko.track.shared.domain.usecases.crud.financials

import com.savenko.track.shared.domain.models.abstractLayer.FinancialEntity
import com.savenko.track.shared.domain.models.expenses.ExpenseItem
import com.savenko.track.shared.domain.models.incomes.IncomeItem
import com.savenko.track.shared.domain.repository.expenses.ExpenseItemRepository
import com.savenko.track.shared.domain.repository.incomes.IncomeItemRepository

class DeleteFinancialItemUseCase(
    private val expenseItemRepositoryImpl: ExpenseItemRepository,
    private val incomeItemRepositoryImpl: IncomeItemRepository
) {
    suspend operator fun invoke(financialItem : FinancialEntity){
        if(financialItem is ExpenseItem){
            expenseItemRepositoryImpl.deleteExpenseItem(financialItem)
        }
        if(financialItem is IncomeItem){
            incomeItemRepositoryImpl.deleteIncomeItem(financialItem)
        }
    }
}