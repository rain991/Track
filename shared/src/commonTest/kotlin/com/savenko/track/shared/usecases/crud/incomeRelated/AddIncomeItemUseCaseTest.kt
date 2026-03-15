package com.savenko.track.shared.domain.usecases.crud.incomeRelated

import com.savenko.track.shared.domain.models.incomes.IncomeItem
import com.savenko.track.shared.testdoubles.FakeIncomeItemRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AddIncomeItemUseCaseTest {
    private val incomeItemRepository = FakeIncomeItemRepository()

    @Test
    fun `income item is added successfully`() = runTest {
        val incomeItem = IncomeItem(
            id = 1,
            currencyTicker = "USD",
            categoryId = 4,
            value = 15f,
            note = "note",
            date = 1000L
        )

        AddIncomeItemUseCase(incomeItemRepository)(incomeItem)

        assertEquals(listOf(incomeItem), incomeItemRepository.added)
    }
}
