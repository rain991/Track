package com.savenko.track.presentation.screens.states.additional.settings.ideasSettings

import com.savenko.track.domain.models.abstractLayer.Idea
import com.savenko.track.domain.models.currency.Currency


/**
 * Ideas settings screen state
 *
 * @property listOfSelectedIdeas user filtered ideas
 * @property isSortedDateDescending If true, listOfSelectedIdeas will be sorted descending
 * @property isShowingCompletedIdeas If true, listOfSelected will show completed ideas
 * @property preferableCurrency user preferable currency
 * @constructor Create empty Ideas settings screen state
 */
data class IdeasSettingsScreenState(
    val listOfSelectedIdeas: List<Idea>,
    val isSortedDateDescending: Boolean,
    val isShowingCompletedIdeas : Boolean,
    val preferableCurrency : Currency
)