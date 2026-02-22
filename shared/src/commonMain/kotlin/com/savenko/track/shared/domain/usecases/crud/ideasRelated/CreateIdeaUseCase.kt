package com.savenko.track.shared.domain.usecases.crud.ideasRelated

import com.savenko.track.shared.domain.models.abstractLayer.Idea
import com.savenko.track.shared.domain.repository.ideas.objectsRepository.IdeaItemRepository

class CreateIdeaUseCase(private val ideaItemRepositoryImpl: IdeaItemRepository) {
    suspend operator fun invoke(idea : Idea) {
        ideaItemRepositoryImpl.addIdea(idea)
    }
}