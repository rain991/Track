package com.savenko.track.domain.usecases.userData.ideas

import com.savenko.track.domain.models.idea.ExpenseLimits
import com.savenko.track.domain.models.idea.IncomePlans
import com.savenko.track.domain.models.idea.Savings
import com.savenko.track.domain.repository.ideas.objectsRepository.IdeaListRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetIdeasListUseCaseTest {
    private lateinit var getIdeasListUseCase: GetIdeasListUseCase
    private val ideaListRepositoryImpl: IdeaListRepository = mock()

    @Before
    fun setUp() {
        getIdeasListUseCase = GetIdeasListUseCase(ideaListRepositoryImpl)
    }

    @After
    fun tearDown() {
        Mockito.reset(ideaListRepositoryImpl)
    }

    @Test
    fun `use case returns combined list of all ideas`() = runTest {
        val incomePlanMock = mock<IncomePlans>()
        val expenseLimitMock = mock<ExpenseLimits>()
        val savingsMock = mock<Savings>()

        val listOfIncomePlans = listOf(incomePlanMock)
        val listOfSavings = listOf(savingsMock)
        val listOfExpenseLimits = listOf(expenseLimitMock)

        whenever(ideaListRepositoryImpl.getIncomesPlansList()).thenReturn(flow {
            emit(listOfIncomePlans)
        })
        whenever(ideaListRepositoryImpl.getSavingsList()).thenReturn(flow {
            emit(listOfSavings)
        })
        whenever(ideaListRepositoryImpl.getExpenseLimitsList()).thenReturn(flow {
            emit(listOfExpenseLimits)
        })

        val result = getIdeasListUseCase.invoke().toList().first()

        val expectedCombinedList = listOfIncomePlans + listOfSavings + listOfExpenseLimits
        assertEquals(expectedCombinedList, result)
    }
}
