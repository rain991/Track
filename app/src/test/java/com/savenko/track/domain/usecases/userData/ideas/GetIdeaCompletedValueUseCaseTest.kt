package com.savenko.track.domain.usecases.userData.ideas

import com.savenko.track.domain.models.abstractLayer.Idea
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

class GetIdeaCompletedValueUseCaseTest {
    private lateinit var getIdeaCompletedValueUseCase: GetIdeaCompletedValueUseCase
    private val ideaListRepositoryImpl: IdeaListRepository = mock()

    @Before
    fun setUp() {
        getIdeaCompletedValueUseCase = GetIdeaCompletedValueUseCase(ideaListRepositoryImpl)
    }

    @After
    fun tearDown() {
        Mockito.reset(ideaListRepositoryImpl)
    }

    @Test
    fun `use case returns correct completed value for an idea`() = runTest {
        val ideaMock = mock<Idea>()
        val expectedCompletedValue = 75.0f

        whenever(ideaListRepositoryImpl.getIdeaCompletedValue(ideaMock)).thenReturn(flow {
            emit(expectedCompletedValue)
        })

        val result = getIdeaCompletedValueUseCase.invoke(ideaMock).toList().first()

        assertEquals(expectedCompletedValue, result)
    }
}
