package com.savenko.track.shared.domain.usecases.userData.ideas

import com.savenko.track.shared.testdoubles.FakeIdeaListRepository
import com.savenko.track.shared.testdoubles.testSavings
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetIdeaCompletedValueUseCaseTest {
    private val ideaListRepository = FakeIdeaListRepository()
    private val useCase = GetIdeaCompletedValueUseCase(ideaListRepository)

    @Test
    fun `use case returns correct completed value for an idea`() = runTest {
        val idea = testSavings(id = 1, completed = false)
        val expectedCompletedValue = 75.0f
        ideaListRepository.ideaCompletedValueById = mapOf(idea.id to expectedCompletedValue)

        val result = useCase.invoke(idea).first()

        assertEquals(expectedCompletedValue, result)
    }
}
