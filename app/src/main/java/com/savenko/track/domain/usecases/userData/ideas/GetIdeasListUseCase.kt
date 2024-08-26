package com.savenko.track.domain.usecases.userData.ideas

import com.savenko.track.domain.models.abstractLayer.Idea
import com.savenko.track.domain.repository.ideas.objectsRepository.IdeaListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetIdeasListUseCase(private val ideaListRepositoryImpl: IdeaListRepository) {
    suspend operator fun invoke(ideaTypes: IdeasTypes = IdeasTypes.All): Flow<List<Idea>> {
        return when (ideaTypes) {
            IdeasTypes.ExpenseLimit -> {
                ideaListRepositoryImpl.getExpenseLimitsList()
            }

            IdeasTypes.Savings -> {
                ideaListRepositoryImpl.getSavingsList()
            }

            IdeasTypes.IncomePlans -> {
                ideaListRepositoryImpl.getIncomesPlansList()
            }

            IdeasTypes.All -> {
                combine(
                    ideaListRepositoryImpl.getIncomesPlansList(),
                    ideaListRepositoryImpl.getSavingsList(),
                    ideaListRepositoryImpl.getExpenseLimitsList()
                ) { incomePlans, savings, expenseLimits ->
                    val allIdeas = mutableListOf<Idea>()
                    allIdeas.addAll(incomePlans)
                    allIdeas.addAll(savings)
                    allIdeas.addAll(expenseLimits)
                    allIdeas
                }
            }
        }
    }

    enum class IdeasTypes {
        ExpenseLimit, Savings, IncomePlans, All
    }
}