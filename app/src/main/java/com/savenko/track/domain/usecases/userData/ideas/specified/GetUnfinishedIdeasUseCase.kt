package com.savenko.track.domain.usecases.userData.ideas.specified

import com.savenko.track.data.implementations.ideas.IdeaListRepositoryImpl
import com.savenko.track.domain.models.abstractLayer.Idea
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine


class GetUnfinishedIdeasUseCase(private val ideaListRepositoryImpl: IdeaListRepositoryImpl) {
    suspend operator fun invoke(): Flow<MutableList<Idea>> {
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