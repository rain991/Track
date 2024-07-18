package com.savenko.track.domain.usecases.crud.ideasRelated

import com.savenko.track.domain.models.abstractLayer.Idea
import com.savenko.track.domain.repository.ideas.objectsRepository.IdeaItemRepository

class CreateIdeaUseCase(private val ideaItemRepositoryImpl: IdeaItemRepository) {
    suspend operator fun invoke(idea : Idea) {
        ideaItemRepositoryImpl.addIdea(idea)
    }
}