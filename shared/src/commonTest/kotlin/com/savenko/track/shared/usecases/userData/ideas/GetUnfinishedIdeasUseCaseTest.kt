package com.savenko.track.shared.domain.usecases.userData.ideas

import com.savenko.track.shared.testdoubles.FakeIdeaListRepository
import com.savenko.track.shared.testdoubles.testExpenseLimit
import com.savenko.track.shared.testdoubles.testIncomePlan
import com.savenko.track.shared.testdoubles.testSavings
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetUnfinishedIdeasUseCaseTest {
    private val ideaListRepository = FakeIdeaListRepository()
    private val useCase = GetUnfinishedIdeasUseCase(ideaListRepository)

    @Test
    fun `use case returns only unfinished ideas`() = runTest {
        ideaListRepository.incomePlans = listOf(testIncomePlan(id = 1, completed = true))
        ideaListRepository.expenseLimits = listOf(testExpenseLimit(id = 2, completed = false))
        ideaListRepository.savings = listOf(testSavings(id = 3, completed = false))

        val result = useCase.invoke().first()

        assertEquals(2, result.size)
        assertEquals(setOf(2, 3), result.map { it.id }.toSet())
    }
}
