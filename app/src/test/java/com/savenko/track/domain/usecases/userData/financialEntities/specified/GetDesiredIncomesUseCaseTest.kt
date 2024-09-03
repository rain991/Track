package com.savenko.track.domain.usecases.userData.financialEntities.specified

import com.savenko.track.domain.models.incomes.IncomeItem
import com.savenko.track.domain.repository.incomes.IncomeListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import java.util.Date
import kotlin.test.assertEquals

class GetDesiredIncomesUseCaseTest {

    private val incomeListRepository: IncomeListRepository = mock()

    private lateinit var getDesiredIncomesUseCase: GetDesiredIncomesUseCase

    @Before
    fun setUp() {
        getDesiredIncomesUseCase = GetDesiredIncomesUseCase(incomeListRepository)
    }

    @After
    fun tearDown() {
        Mockito.reset(incomeListRepository)
    }

    @Test
    fun `invoke should return expected income items for given time period`() = runTest {
        val lowerDate = Date(3000)
        val upperDate = Date(10000)

        val incomeItems = listOf(
            IncomeItem(currencyTicker = "UAH", value = 200f, note = "", categoryId = 2, date = Date(6000))
        )
        val expectedFlow: Flow<List<IncomeItem>> = flowOf(incomeItems)

        `when`(incomeListRepository.getIncomesInTimeSpanDateDesc(lowerDate.time, upperDate.time)).thenReturn(
            expectedFlow
        )

        val result = getDesiredIncomesUseCase(lowerDate.time, upperDate.time).first()
        val expected = incomeItems.filter { it.date.time in lowerDate.time..upperDate.time }
        assertEquals(expected, result)
        verify(incomeListRepository).getIncomesInTimeSpanDateDesc(lowerDate.time, upperDate.time)
        verifyNoMoreInteractions(incomeListRepository)
    }
}
