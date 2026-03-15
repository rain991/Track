package com.savenko.track.shared.domain.usecases.crud.expenseRelated

import com.savenko.track.shared.domain.models.expenses.ExpenseItem
import com.savenko.track.shared.testdoubles.FakeExpenseItemRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AddExpenseItemUseCaseTest {
    private val expenseItemRepository = FakeExpenseItemRepository()

    @Test
    fun `expense item added correctly`() = runTest {
        val expenseItem = ExpenseItem(
            id = 1,
            currencyTicker = "USD",
            value = 42f,
            note = "note",
            date = 1000L,
            categoryId = 5
        )

        AddExpenseItemUseCase(expenseItemRepository).invoke(expenseItem)

        assertEquals(listOf(expenseItem), expenseItemRepository.added)
    }
}
