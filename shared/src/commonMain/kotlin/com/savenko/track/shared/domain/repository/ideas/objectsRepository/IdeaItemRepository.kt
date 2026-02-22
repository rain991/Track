package com.savenko.track.shared.domain.repository.ideas.objectsRepository

import com.savenko.track.shared.domain.models.abstractLayer.Idea
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface IdeaItemRepository {
    suspend fun addIdea(idea: Idea, context: CoroutineContext = Dispatchers.Default)
    suspend fun editIdea(idea: Idea, context: CoroutineContext = Dispatchers.Default)
    suspend fun deleteIdea(idea: Idea, context: CoroutineContext = Dispatchers.Default)
    suspend fun updateIdea(idea: Idea)
}