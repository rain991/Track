package com.example.track.domain.usecases.ideasRelated

import com.example.track.data.implementations.ideas.IdeaItemRepositoryImpl
import com.example.track.domain.models.abstractLayer.Idea

class CreateIdeaUseCase(private val ideaItemRepositoryImpl: IdeaItemRepositoryImpl) {
    suspend operator fun invoke(idea : Idea) {
        ideaItemRepositoryImpl.addIdea(idea)
    }
}