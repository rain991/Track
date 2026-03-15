package com.savenko.track.shared.domain.usecases.crud.ideasRelated

import com.savenko.track.shared.testdoubles.FakeIdeaItemRepository
import com.savenko.track.shared.testdoubles.testExpenseLimit
import com.savenko.track.shared.testdoubles.testIncomePlan
import com.savenko.track.shared.testdoubles.testSavings
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CreateIdeaUseCaseTest {
    private val ideaItemRepository = FakeIdeaItemRepository()

    @Test
    fun `savings are added correctly`() = runTest {
        val idea = testSavings(id = 1, completed = false)

        CreateIdeaUseCase(ideaItemRepository).invoke(idea)

        assertEquals(idea, ideaItemRepository.added.single())
    }

    @Test
    fun `incomePlans are added correctly`() = runTest {
        val idea = testIncomePlan(id = 2, completed = false)

        CreateIdeaUseCase(ideaItemRepository).invoke(idea)

        assertEquals(idea, ideaItemRepository.added.last())
    }

    @Test
    fun `expenseLimits are added correctly`() = runTest {
        val idea = testExpenseLimit(id = 3, completed = false)

        CreateIdeaUseCase(ideaItemRepository).invoke(idea)

        assertEquals(idea, ideaItemRepository.added.last())
    }
}
