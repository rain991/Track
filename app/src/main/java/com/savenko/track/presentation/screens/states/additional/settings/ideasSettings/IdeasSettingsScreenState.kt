package com.savenko.track.presentation.screens.states.additional.settings.ideasSettings

import com.savenko.track.domain.models.abstractLayer.Idea
import com.savenko.track.domain.models.currency.Currency

data class IdeasSettingsScreenState(
    val listOfSelectedIdeas: List<Idea>,
    val isSortedDateDescending: Boolean,
    val isShowingCompletedIdeas : Boolean,
    val preferableCurrency : Currency
)