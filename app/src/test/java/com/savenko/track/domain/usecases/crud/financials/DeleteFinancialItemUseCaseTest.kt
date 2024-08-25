package com.savenko.track.domain.usecases.crud.financials

import com.savenko.track.domain.models.abstractLayer.FinancialEntity
import com.savenko.track.domain.models.expenses.ExpenseItem
import com.savenko.track.domain.models.incomes.IncomeItem
import com.savenko.track.domain.repository.expenses.ExpenseItemRepository
import com.savenko.track.domain.repository.incomes.IncomeItemRepository
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions

class DeleteFinancialItemUseCaseTest {
    private lateinit var deleteFinancialItemUseCase: DeleteFinancialItemUseCase
    private val expenseItemRepositoryImpl: ExpenseItemRepository = mock()
    private val incomeItemRepositoryImpl: IncomeItemRepository = mock()

    @Before
    fun setup() {
        deleteFinancialItemUseCase = DeleteFinancialItemUseCase(
            expenseItemRepositoryImpl = expenseItemRepositoryImpl,
            incomeItemRepositoryImpl = incomeItemRepositoryImpl
        )
    }

    @After
    fun tearDown() {
        Mockito.reset(expenseItemRepositoryImpl, incomeItemRepositoryImpl)
    }

    @Test
    fun `should delete ExpenseItem when invoked with an ExpenseItem`() = runTest {
        val expenseItem = mock<ExpenseItem>()

        deleteFinancialItemUseCase(expenseItem)

        verify(expenseItemRepositoryImpl).deleteExpenseItem(expenseItem)
        verifyNoInteractions(incomeItemRepositoryImpl)
    }

    @Test
    fun `should delete IncomeItem when invoked with an IncomeItem`() = runTest {
        val incomeItem = mock<IncomeItem>()

        deleteFinancialItemUseCase(incomeItem)

        verify(incomeItemRepositoryImpl).deleteIncomeItem(incomeItem)
        verifyNoInteractions(expenseItemRepositoryImpl)
    }

    @Test
    fun `should not interact with repositories when invoked with an unsupported object`() = runTest {
        val unsupportedItem = mock<UnsupportedFinancialItem>()

        deleteFinancialItemUseCase(unsupportedItem)

        verifyNoInteractions(expenseItemRepositoryImpl)
        verifyNoInteractions(incomeItemRepositoryImpl)
    }

    abstract class UnsupportedFinancialItem : FinancialEntity()
}