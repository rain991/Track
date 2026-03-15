package com.savenko.track.shared.domain.usecases.crud.financials

import com.savenko.track.shared.domain.models.abstractLayer.FinancialEntity
import com.savenko.track.shared.domain.models.expenses.ExpenseItem
import com.savenko.track.shared.domain.models.incomes.IncomeItem
import com.savenko.track.shared.testdoubles.FakeExpenseItemRepository
import com.savenko.track.shared.testdoubles.FakeIncomeItemRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DeleteFinancialItemUseCaseTest {
    private val expenseItemRepository = FakeExpenseItemRepository()
    private val incomeItemRepository = FakeIncomeItemRepository()

    private val useCase = DeleteFinancialItemUseCase(
        expenseItemRepositoryImpl = expenseItemRepository,
        incomeItemRepositoryImpl = incomeItemRepository
    )

    @Test
    fun `should delete ExpenseItem when invoked with an ExpenseItem`() = runTest {
        val expenseItem = ExpenseItem(
            id = 1,
            currencyTicker = "USD",
            value = 10f,
            note = "",
            date = 1000L,
            categoryId = 1
        )

        useCase(expenseItem)

        assertEquals(listOf(expenseItem), expenseItemRepository.deleted)
        assertTrue(incomeItemRepository.deleted.isEmpty())
    }

    @Test
    fun `should delete IncomeItem when invoked with an IncomeItem`() = runTest {
        val incomeItem = IncomeItem(
            id = 1,
            currencyTicker = "USD",
            categoryId = 2,
            value = 10f,
            note = "",
            date = 1000L
        )

        useCase(incomeItem)

        assertEquals(listOf(incomeItem), incomeItemRepository.deleted)
        assertTrue(expenseItemRepository.deleted.isEmpty())
    }

    @Test
    fun `should not interact with repositories when invoked with an unsupported object`() = runTest {
        useCase(UnsupportedFinancialItem())

        assertTrue(expenseItemRepository.deleted.isEmpty())
        assertTrue(incomeItemRepository.deleted.isEmpty())
    }

    private class UnsupportedFinancialItem : FinancialEntity() {
        override val id: Int = 0
        override val currencyTicker: String = "USD"
        override val value: Float = 0f
        override val note: String = ""
        override val date: Long = 0L
        override val disabled: Boolean = false
        override val categoryId: Int = 0
    }
}
