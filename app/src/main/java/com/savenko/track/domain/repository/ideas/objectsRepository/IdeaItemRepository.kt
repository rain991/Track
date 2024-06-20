package com.savenko.track.domain.repository.ideas.objectsRepository

import com.savenko.track.domain.models.abstractLayer.Idea
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface IdeaItemRepository {
    suspend fun addIdea(idea: Idea, context: CoroutineContext = Dispatchers.IO)
    suspend fun editIdea(idea: Idea, context: CoroutineContext = Dispatchers.IO)
    suspend fun deleteIdea(idea: Idea, context: CoroutineContext = Dispatchers.IO)
    suspend fun updateIdea(idea: Idea)
}