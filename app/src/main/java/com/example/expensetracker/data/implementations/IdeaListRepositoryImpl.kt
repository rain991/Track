package com.example.expensetracker.data.implementations

import com.example.expensetracker.data.models.idea.Idea
import com.example.expensetracker.domain.repository.IdeaListRepository
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class IdeaListRepositoryImpl : IdeaListRepository {
    override suspend fun getIdeasList(context: CoroutineContext): Flow<List<Idea>> {
        TODO("Not yet implemented")
    }

    override suspend fun addIdea(idea: Idea, context: CoroutineContext) {
        TODO("Not yet implemented")
    }

    override suspend fun editIdea(idea: Idea, context: CoroutineContext) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteIdea(idea: Idea, context: CoroutineContext) {
        TODO("Not yet implemented")
    }
}