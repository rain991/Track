package com.savenko.track.shared.domain.usecases.userData.financialEntities.specified

import com.savenko.track.shared.domain.models.expenses.ExpenseItem
import com.savenko.track.shared.testdoubles.FakeExpensesListRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Instant

class GetDesiredExpensesUseCaseTest {
    private val expensesListRepository = FakeExpensesListRepository()
    private val useCase = GetDesiredExpensesUseCase(expensesListRepository)

    @Test
    fun `returns expenses in desired time span`() = runTest {
        expensesListRepository.expenses = listOf(
            ExpenseItem(id = 1, currencyTicker = "USD", value = 55f, note = "", date = 2000L, categoryId = 5),
            ExpenseItem(id = 2, currencyTicker = "TRY", value = 30f, note = "s", date = 5000L, categoryId = 5)
        )

        val startOfSpan = 4000L
        val endOfSpan = 10000L
        val datesRange = Instant.fromEpochMilliseconds(startOfSpan)..Instant.fromEpochMilliseconds(endOfSpan)

        val actual = useCase(datesRange).first()
        val expected = expensesListRepository.expenses.filter { it.date in startOfSpan..endOfSpan }
            .sortedByDescending { it.date }

        assertEquals(expected, actual)
    }
}
