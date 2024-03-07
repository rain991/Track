package com.example.track.domain.repository

import com.example.track.data.models.idea.Idea
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

interface IdeaListRepository {
    suspend fun getIdeasList(context: CoroutineContext = Dispatchers.IO): Flow<List<Idea>>
    suspend fun addIdea(idea: Idea, context: CoroutineContext = Dispatchers.IO)
    suspend fun editIdea(idea: Idea, context: CoroutineContext = Dispatchers.IO)
    suspend fun deleteIdea(idea: Idea, context: CoroutineContext = Dispatchers.IO)
}