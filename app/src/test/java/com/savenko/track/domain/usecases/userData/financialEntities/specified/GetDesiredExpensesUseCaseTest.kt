package com.savenko.track.domain.usecases.userData.financialEntities.specified

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
import org.mockito.kotlin.mock
import kotlin.time.Instant

class GetDesiredExpensesUseCaseTest {
    private lateinit var getDesiredExpensesUseCase: GetDesiredExpensesUseCase
    private val expensesListRepositoryImpl: ExpensesListRepository = mock()


    @Before
    fun setup() {
        getDesiredExpensesUseCase = GetDesiredExpensesUseCase(expensesListRepositoryImpl)
    }

    @After
    fun tearDown() {
        Mockito.reset(expensesListRepositoryImpl)
    }

    @Test
    fun `returns expenses in desired time span`() = runTest {
        val listOfExpenses = listOf(
            ExpenseItem(
                currencyTicker = "USD",
                value = 55f,
                note = "",
                categoryId = 5,
                date = 2000L
            ),
            ExpenseItem(
                currencyTicker = "TRY",
                value = 30f,
                note = "s",
                categoryId = 5,
                date = 5000L
            )
        )

        val startOfSpan = 4000L
        val endOfSpan = 10000L
        val datesRange =
            Instant.fromEpochMilliseconds(startOfSpan)..Instant.fromEpochMilliseconds(endOfSpan)
        Mockito.`when`(
            expensesListRepositoryImpl.getExpensesListInTimeSpanDateDesc(
                startOfSpan,
                endOfSpan
            )
        )
            .thenReturn(
                flow { emit(listOfExpenses.filter { it.date in startOfSpan..endOfSpan }) }
            )


        val actual = getDesiredExpensesUseCase(datesRange).first()
        val expected = listOfExpenses.filter { it.date in startOfSpan..endOfSpan }

        assertEquals(expected, actual)
    }
}