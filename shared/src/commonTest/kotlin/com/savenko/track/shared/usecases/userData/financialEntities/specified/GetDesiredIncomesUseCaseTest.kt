package com.savenko.track.shared.domain.usecases.userData.financialEntities.specified

import com.savenko.track.shared.domain.models.incomes.IncomeItem
import com.savenko.track.shared.testdoubles.FakeIncomeListRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetDesiredIncomesUseCaseTest {
    private val incomeListRepository = FakeIncomeListRepository()
    private val useCase = GetDesiredIncomesUseCase(incomeListRepository)

    @Test
    fun `invoke should return expected income items for given time period`() = runTest {
        val lowerDate = 3000L
        val upperDate = 10000L

        val incomeItems = listOf(
            IncomeItem(id = 1, currencyTicker = "UAH", categoryId = 2, value = 200f, note = "", date = 6000L)
        )
        incomeListRepository.incomes = incomeItems

        val result = useCase(lowerDate, upperDate).first()
        val expected = incomeItems.filter { it.date in lowerDate..upperDate }

        assertEquals(expected, result)
    }
}
