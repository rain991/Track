package com.example.expensetracker.data.implementations

import com.example.expensetracker.data.database.IdeaDao
import com.example.expensetracker.data.models.idea.Idea
import com.example.expensetracker.domain.repository.IdeaListRepository
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