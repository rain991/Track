package com.savenko.track.domain.usecases.expenseRelated

import com.savenko.track.data.implementations.expenses.ExpenseItemRepositoryImpl
import com.savenko.track.domain.models.expenses.ExpenseItem
import com.savenko.track.domain.usecases.crud.expenseRelated.AddExpenseItemUseCase
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class AddExpenseItemUseCaseTest {

    private val expenseItemRepositoryImpl = mock<ExpenseItemRepositoryImpl>()

    @Test
    fun `expense item added correctly`() =  runBlocking{
        val expenseItem = mock<ExpenseItem>()
        val addExpenseItemUseCase = AddExpenseItemUseCase(expenseItemRepositoryImpl)
        addExpenseItemUseCase.invoke(expenseItem)
        verify(expenseItemRepositoryImpl).addExpensesItem(expenseItem)
    }
}