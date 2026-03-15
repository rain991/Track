package com.savenko.track.shared.domain.usecases.userData.financialEntities.nonSpecified

import com.savenko.track.shared.domain.models.expenses.ExpenseItem
import com.savenko.track.shared.testdoubles.FakeExpensesListRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetUserExpensesUseCaseTest {
    private val expensesListRepository = FakeExpensesListRepository()
    private val useCase = GetUserExpensesUseCase(expensesListRepository)

    @Test
    fun `use case returns expenses list sorted dates DESC`() = runTest {
        expensesListRepository.expenses = listOf(
            ExpenseItem(id = 1, currencyTicker = "USD", value = 55f, note = "", date = 2000L, categoryId = 5),
            ExpenseItem(id = 2, currencyTicker = "TRY", value = 30f, note = "s", date = 5000L, categoryId = 5)
        )

        val actual = useCase.getUserExpensesDateDesc().first()
        val expected = expensesListRepository.expenses.sortedByDescending { it.date }

        assertEquals(expected, actual)
    }
}
