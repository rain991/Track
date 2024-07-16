package com.savenko.track.domain.usecases.crud.financials

import com.savenko.track.data.implementations.expenses.expenseItem.ExpenseItemRepositoryImpl
import com.savenko.track.data.implementations.incomes.incomeItem.IncomeItemRepositoryImpl
import com.savenko.track.domain.models.abstractLayer.FinancialEntity
import com.savenko.track.domain.models.expenses.ExpenseItem
import com.savenko.track.domain.models.incomes.IncomeItem

class DeleteFinancialItemUseCase(
    private val expenseItemRepositoryImpl: ExpenseItemRepositoryImpl,
    private val incomeItemRepositoryImpl: IncomeItemRepositoryImpl
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