package com.savenko.track.domain.usecases.userData.ideas

import com.savenko.track.domain.models.abstractLayer.Idea
import com.savenko.track.domain.repository.ideas.objectsRepository.IdeaListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine


class GetUnfinishedIdeasUseCase(private val ideaListRepositoryImpl: IdeaListRepository) {
    suspend operator fun invoke(): Flow<List<Idea>> {
        return combine(
            ideaListRepositoryImpl.getIncomesPlansList(),
            ideaListRepositoryImpl.getSavingsList(),
            ideaListRepositoryImpl.getExpenseLimitsList()
        ) { incomePlans, savings, expenseLimits ->
            val allIdeas = mutableListOf<Idea>()
            allIdeas.addAll(incomePlans.filter { !it.completed })
            allIdeas.addAll(savings.filter { !it.completed })
            allIdeas.addAll(expenseLimits.filter { !it.completed })
            allIdeas
        }
    }
}