package com.savenko.track.domain.usecases.userData.financialEntities.specified

import android.util.Range
import com.savenko.track.domain.models.expenses.ExpenseItem
import com.savenko.track.domain.repository.expenses.ExpensesListRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.kotlin.mock
import java.util.Date

//@RunWith(RobolectricTestRunner::class)
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

    @Ignore("To be fixed in TW-18")
    @Test
    fun `returns expenses in desired time span`() = runTest {
        val listOfExpenses = listOf(
            ExpenseItem(currencyTicker = "USD", value = 55f, note = "", categoryId = 5, date = Date(2000)),
            ExpenseItem(currencyTicker = "TRY", value = 30f, note = "s", categoryId = 5, date = Date(5000))
        )

        val startOfSpan = Date(4000)
        val endOfSpan = Date(10000)
        val datesRange = Range(startOfSpan, endOfSpan)
        Mockito.`when`(expensesListRepositoryImpl.getExpensesListInTimeSpanDateDesc(startOfSpan.time, endOfSpan.time))
            .thenReturn(
                flow { emit(listOfExpenses.filter { it.date.time in startOfSpan.time..endOfSpan.time }) }
            )


        val actual = getDesiredExpensesUseCase(datesRange).first()
        val expected = listOfExpenses.filter { it.date.time in startOfSpan.time..endOfSpan.time }

        assertEquals(expected, actual)
    }
}