package com.savenko.track.domain.usecases.userData.financialEntities.nonSpecified

import com.savenko.track.domain.models.expenses.ExpenseItem
import com.savenko.track.domain.repository.expenses.ExpensesListRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.util.Date

class GetUserExpensesUseCaseTest {
    private lateinit var getUserExpensesUseCase: GetUserExpensesUseCase
    private val expensesListRepository: ExpensesListRepository = mock()

    @Before
    fun setup() {
        getUserExpensesUseCase = GetUserExpensesUseCase(expensesListRepository)
    }

    @After
    fun tearDown() {
        Mockito.reset(expensesListRepository)
    }


    @Test
    fun `use case returns expenses list sorted dates DESC`() = runTest {
        val listOfExpenses = listOf(
            ExpenseItem(currencyTicker = "USD", value = 55f, note = "", categoryId = 5, date = Date(2000)),
            ExpenseItem(currencyTicker = "TRY", value = 30f, note = "s", categoryId = 5, date = Date(5000))
        )
        Mockito.`when`(expensesListRepository.getSortedExpensesListDateDesc())
            .thenReturn(flow { emit(listOfExpenses.sortedByDescending { it.date }) })

        val actual = getUserExpensesUseCase.getUserExpensesDateDesc().first()
        val expected = listOfExpenses.sortedByDescending { it.date }
        assertEquals(expected, actual)
    }
}