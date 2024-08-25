package com.savenko.track.domain.usecases.crud.expenseRelated

import com.savenko.track.data.implementations.expenses.expenseItem.ExpenseItemRepositoryImpl
import com.savenko.track.domain.models.expenses.ExpenseItem
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class AddExpenseItemUseCaseTest {
    private val expenseItemRepositoryImpl = mock<ExpenseItemRepositoryImpl>()

    @Test
    fun `expense item added correctly`() = runTest {
        val expenseItem = mock<ExpenseItem>()
        val addExpenseItemUseCase = AddExpenseItemUseCase(expenseItemRepositoryImpl)
        addExpenseItemUseCase.invoke(expenseItem)
        verify(expenseItemRepositoryImpl).addExpensesItem(expenseItem)
    }
}