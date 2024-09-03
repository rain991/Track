package com.savenko.track.domain.usecases.crud.ideasRelated

import com.savenko.track.data.implementations.ideas.IdeaItemRepositoryImpl
import com.savenko.track.domain.models.idea.ExpenseLimits
import com.savenko.track.domain.models.idea.IncomePlans
import com.savenko.track.domain.models.idea.Savings
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

class CreateIdeaUseCaseTest {
    private lateinit var createIdeaUseCase: CreateIdeaUseCase
    private val ideaItemRepositoryImpl = mock<IdeaItemRepositoryImpl>()

    @Before
    fun setup() {
        createIdeaUseCase = CreateIdeaUseCase(ideaItemRepositoryImpl)
    }

    @After
    fun tearDown() {
        Mockito.reset(ideaItemRepositoryImpl)
    }

    @Test
    fun `savings are added correctly`() = runTest {
        val idea = mock<Savings>()
        createIdeaUseCase.invoke(idea)
        verify(ideaItemRepositoryImpl).addIdea(idea)
    }

    @Test
    fun `incomePlans are added correctly`() = runTest {
        val idea = mock<IncomePlans>()
        createIdeaUseCase.invoke(idea)
        verify(ideaItemRepositoryImpl).addIdea(idea)
    }

    @Test
    fun `expenseLimits are added correctly`() = runTest {
        val idea = mock<ExpenseLimits>()
        createIdeaUseCase.invoke(idea)
        verify(ideaItemRepositoryImpl).addIdea(idea)
    }
}