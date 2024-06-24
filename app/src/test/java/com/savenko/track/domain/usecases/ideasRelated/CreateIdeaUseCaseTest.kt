package com.savenko.track.domain.usecases.ideasRelated

import com.savenko.track.data.implementations.ideas.IdeaItemRepositoryImpl
import com.savenko.track.domain.models.idea.ExpenseLimits
import com.savenko.track.domain.models.idea.IncomePlans
import com.savenko.track.domain.models.idea.Savings
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

class CreateIdeaUseCaseTest {

    private val ideaItemRepositoryImpl = mock<IdeaItemRepositoryImpl>()

    @Test
    fun `savings are added correctly`() = runBlocking {
        val idea = mock<Savings>()
        val createIdeaUseCase = CreateIdeaUseCase(ideaItemRepositoryImpl)
        createIdeaUseCase.invoke(idea)
        verify(ideaItemRepositoryImpl).addIdea(idea)
    }

    @Test
    fun `incomePlans are added correctly`() = runBlocking {
        val idea = mock<IncomePlans>()
        val createIdeaUseCase = CreateIdeaUseCase(ideaItemRepositoryImpl)
        createIdeaUseCase.invoke(idea)
        verify(ideaItemRepositoryImpl).addIdea(idea)
    }

    @Test
    fun `expenseLimits are added correctly`() = runBlocking {
        val idea = mock<ExpenseLimits>()
        val createIdeaUseCase = CreateIdeaUseCase(ideaItemRepositoryImpl)
        createIdeaUseCase.invoke(idea)
        verify(ideaItemRepositoryImpl).addIdea(idea)
    }
}