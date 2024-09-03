package com.savenko.track.domain.usecases.userData.ideas

import com.savenko.track.domain.models.idea.ExpenseLimits
import com.savenko.track.domain.models.idea.IncomePlans
import com.savenko.track.domain.models.idea.Savings
import com.savenko.track.domain.repository.ideas.objectsRepository.IdeaListRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetUnfinishedIdeasUseCaseTest {
    private lateinit var getUnfinishedIdeasUseCase: GetUnfinishedIdeasUseCase
    private val ideaListRepositoryImpl: IdeaListRepository = mock()

    @Before
    fun setUp() {
        getUnfinishedIdeasUseCase = GetUnfinishedIdeasUseCase(ideaListRepositoryImpl)
    }

    @After
    fun tearDown() {
        Mockito.reset(ideaListRepositoryImpl)
    }

    @Test
    fun `use case returns only unfinished ideas`() = runTest {
        val incomePlanMock = mock<IncomePlans>()
        whenever(incomePlanMock.completed).thenReturn(true)

        val expenseLimitMock = mock<ExpenseLimits>()
        whenever(expenseLimitMock.completed).thenReturn(false)

        val savingsMock = mock<Savings>()
        whenever(savingsMock.completed).thenReturn(false)

        val listOfIdeas = listOf(incomePlanMock, expenseLimitMock, savingsMock)

        whenever(ideaListRepositoryImpl.getIncomesPlansList()).thenReturn(flow {
            emit(listOfIdeas.filterIsInstance<IncomePlans>())
        })
        whenever(ideaListRepositoryImpl.getSavingsList()).thenReturn(flow {
            emit(listOfIdeas.filterIsInstance<Savings>())
        })
        whenever(ideaListRepositoryImpl.getExpenseLimitsList()).thenReturn(flow {
            emit(listOfIdeas.filterIsInstance<ExpenseLimits>())
        })

        val result = getUnfinishedIdeasUseCase.invoke().first()

        assertEquals(listOfIdeas.filter { !it.completed }.size, result.size)
    }
}
