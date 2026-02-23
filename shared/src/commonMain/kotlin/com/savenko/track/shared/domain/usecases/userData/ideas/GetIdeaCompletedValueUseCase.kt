package com.savenko.track.shared.domain.usecases.userData.ideas

import com.savenko.track.shared.domain.models.abstractLayer.Idea
import com.savenko.track.shared.domain.repository.ideas.objectsRepository.IdeaListRepository
import kotlinx.coroutines.flow.Flow

class GetIdeaCompletedValueUseCase(private val ideaListRepositoryImpl: IdeaListRepository) {
    suspend operator fun invoke(idea : Idea) : Flow<Float> {
        return ideaListRepositoryImpl.getIdeaCompletedValue(idea)
    }
}