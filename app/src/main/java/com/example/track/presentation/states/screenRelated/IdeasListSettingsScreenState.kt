package com.example.track.presentation.states.screenRelated

import com.example.track.domain.models.abstractLayer.Idea

data class IdeasListSettingsScreenState(
    val listOfSelectedIdeas: List<Idea>,
    val isSortedDateDescending: Boolean,
    val isShowingCompletedIdeas : Boolean
)