package com.savenko.track.shared.domain.usecases.userData.financialEntities.nonSpecified

import com.savenko.track.shared.domain.models.incomes.IncomeItem
import com.savenko.track.shared.testdoubles.FakeIncomeListRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetUserIncomesUseCaseTest {
    private val incomeListRepository = FakeIncomeListRepository()
    private val useCase = GetUserIncomesUseCase(incomeListRepository)

    @Test
    fun `use case returns incomes list sorted dates DESC`() = runTest {
        incomeListRepository.incomes = listOf(
            IncomeItem(id = 1, currencyTicker = "USD", categoryId = 5, value = 55f, note = "", date = 2000L),
            IncomeItem(id = 2, currencyTicker = "TRY", categoryId = 5, value = 30f, note = "s", date = 5000L)
        )

        val actual = useCase.getUserIncomesDateDesc().first()
        val expected = incomeListRepository.incomes.sortedByDescending { it.date }

        assertEquals(expected, actual)
    }
}
