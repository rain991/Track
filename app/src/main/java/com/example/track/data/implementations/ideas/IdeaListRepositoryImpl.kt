package com.example.track.data.implementations.ideas

import com.example.track.data.database.ideaRelated.IdeaDao
import com.example.track.data.models.idea.Idea
import com.example.track.domain.repository.ideas.IdeaListRepository
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class IdeaListRepositoryImpl(private val ideaDao: IdeaDao) : IdeaListRepository {
    override suspend fun getIdeasList(context: CoroutineContext): Flow<List<Idea>> {
        return ideaDao.getAllData()
    }

    override suspend fun addIdea(idea: Idea, context: CoroutineContext) {
        ideaDao.insert(idea)
    }

    override suspend fun editIdea(idea: Idea, context: CoroutineContext) {
        ideaDao.update(idea)
    }

    override suspend fun deleteIdea(idea: Idea, context: CoroutineContext) {
        ideaDao.delete(idea)
    }
}