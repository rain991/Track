package com.savenko.track.shared.domain.usecases.userData.ideas

import com.savenko.track.shared.testdoubles.FakeIdeaListRepository
import com.savenko.track.shared.testdoubles.testExpenseLimit
import com.savenko.track.shared.testdoubles.testIncomePlan
import com.savenko.track.shared.testdoubles.testSavings
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetIdeasListUseCaseTest {
    private val ideaListRepository = FakeIdeaListRepository()
    private val useCase = GetIdeasListUseCase(ideaListRepository)

    @Test
    fun `use case returns combined list of all ideas`() = runTest {
        val incomePlan = testIncomePlan(id = 1, completed = false)
        val expenseLimit = testExpenseLimit(id = 2, completed = false)
        val savings = testSavings(id = 3, completed = false)

        ideaListRepository.incomePlans = listOf(incomePlan)
        ideaListRepository.savings = listOf(savings)
        ideaListRepository.expenseLimits = listOf(expenseLimit)

        val result = useCase.invoke().first()

        assertEquals(listOf(incomePlan, savings, expenseLimit), result)
    }
}
