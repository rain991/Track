package com.savenko.track.domain.usecases.userData.financialEntities.nonSpecified

import com.savenko.track.domain.models.incomes.IncomeItem
import com.savenko.track.domain.repository.incomes.IncomeListRepository
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

class GetUserIncomesUseCaseTest {
    private lateinit var getUserIncomesUseCase : GetUserIncomesUseCase
    private val incomeListRepositoryImpl: IncomeListRepository = mock()


    @Before
    fun setup(){
        getUserIncomesUseCase = GetUserIncomesUseCase(incomeListRepositoryImpl)
    }

    @After
    fun tearDown() {
        Mockito.reset(incomeListRepositoryImpl)
    }

    @Test
    fun `use case returns incomes list sorted dates DESC`() = runTest {
        val listOfIncomes = listOf(
            IncomeItem(currencyTicker = "USD", value = 55f, note = "", categoryId = 5, date = Date(2000)),
            IncomeItem(currencyTicker = "TRY", value = 30f, note = "s", categoryId = 5, date = Date(5000))
        )
        Mockito.`when`(incomeListRepositoryImpl.getSortedIncomesListDateDesc())
            .thenReturn(flow { emit(listOfIncomes.sortedByDescending { it.date }) })

        val actual = getUserIncomesUseCase.getUserIncomesDateDesc().first()
        val expected = listOfIncomes.sortedByDescending { it.date }
        assertEquals(expected, actual)
    }
}