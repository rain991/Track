package com.savenko.track.domain.usecases.ideasRelated

import com.savenko.track.data.implementations.ideas.IdeaItemRepositoryImpl
import com.savenko.track.domain.models.abstractLayer.Idea

class CreateIdeaUseCase(private val ideaItemRepositoryImpl: IdeaItemRepositoryImpl) {
    suspend operator fun invoke(idea : Idea) {
        ideaItemRepositoryImpl.addIdea(idea)
    }
}