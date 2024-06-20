package com.savenko.track.presentation.states.screenRelated

import com.savenko.track.domain.models.abstractLayer.Idea

data class IdeasListSettingsScreenState(
    val listOfSelectedIdeas: List<Idea>,
    val isSortedDateDescending: Boolean,
    val isShowingCompletedIdeas : Boolean
)